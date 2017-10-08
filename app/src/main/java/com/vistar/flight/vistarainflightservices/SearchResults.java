package com.vistar.flight.vistarainflightservices;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Apoorva Jaiswal on 10/7/2017.
 */

public class SearchResults extends Activity {
    String mLocation="Kolkata";
    int count=0;
    String typeToSearch="restaurant";
    String photo_ref="";
    ArrayList<String> names= new ArrayList<>();
    ArrayList<String> references= new ArrayList<>();
    ImageView im[]= new ImageView[4];
    TextView tv[] = new TextView[4];
    TextView txt1;
    int type1,type2,type3,type4,type5,type6;
    RelativeLayout rl[] = new RelativeLayout[4];
    FloatingActionButton floatingNextButton;
    String choices[] = new String[7];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);
        mLocation=getIntent().getExtras().getString("place");
        typeToSearch=getIntent().getExtras().getString("type");
        type1 = getIntent().getIntExtra("type1", 0);
        type2 = getIntent().getIntExtra("type2", 0);
        type3 = getIntent().getIntExtra("type3", 0);
        type4 = getIntent().getIntExtra("type4", 0);
        type5 = getIntent().getIntExtra("type5", 0);
        type6 = getIntent().getIntExtra("type6", 0);
        for(int i=1; i<=6; i++){
            choices[i] = getIntent().getExtras().getString("choice"+i);
        }

        im[0]=(ImageView)findViewById(R.id.im1);
        im[1]=(ImageView)findViewById(R.id.im2);
        im[2]=(ImageView)findViewById(R.id.im3);
        im[3]=(ImageView)findViewById(R.id.im4);

        tv[0] = (TextView)findViewById(R.id.t1);
        tv[1] = (TextView)findViewById(R.id.t2);
        tv[2] = (TextView)findViewById(R.id.t3);
        tv[3] = (TextView)findViewById(R.id.t4);

        rl[0] = (RelativeLayout)findViewById(R.id.rl1);
        rl[1] = (RelativeLayout)findViewById(R.id.rl2);
        rl[2] = (RelativeLayout)findViewById(R.id.rl3);
        rl[3] = (RelativeLayout)findViewById(R.id.rl4);

        txt1 = (TextView)findViewById(R.id.txt1);
        txt1.setText("THESE ARE THE "+typeToSearch.toUpperCase()+" WE FOUND FOR YOU");

        floatingNextButton = (FloatingActionButton)findViewById(R.id.floatingNextButton);
        floatingNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchResults.this, GetLatLongFromCityName.class);
                i.putExtra("type1",type1);
                i.putExtra("type2",type2);
                i.putExtra("type3",type3);
                i.putExtra("type4",type4);
                i.putExtra("type5",type5);
                i.putExtra("type6",type6);
                i.putExtra("place", mLocation);
                for(int ii=1; ii<=6; ii++){
                    i.putExtra("choice"+ii, choices[ii]);
                }
                startActivity(i);
            }
        });
        //fetch places
        StringBuilder sbValue = new StringBuilder(sbMethod("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+typeToSearch+"+in+"+mLocation+"&key=AIzaSyATJSb81r1r8JVWKhMH76clBsjfqKfzAxA"));
        PlacesTask placesTask = new PlacesTask();
        placesTask.execute(sbValue.toString());

    }



    public StringBuilder sbMethod(String m) {

        //use your current location here
        StringBuilder sb = new StringBuilder(m);
        Log.d("Map", "api: " + sb.toString());
        return sb;
    }


    private class PlacesTask extends AsyncTask<String, Integer, String> {

        String data = null;
        /*ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SearchResults.this);
            progressDialog.setCancelable(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }*/

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParserTask
            parserTask.execute(result);
            //ParserTask1(new String[]{result});
            //progressDialog.hide();
        }
    }
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Downloading Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;

    }

    public class Place_JSON {

        /**
         * Receives a JSONObject and returns a list
         */
        public List<HashMap<String, String>> parse(JSONObject jObject) {

            JSONArray jPlaces = null;
            try {
                /** Retrieves all the elements in the 'places' array */
                jPlaces = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /** Invoking getPlaces with the array of json object
             * where each json object represent a place
             */
            return getPlaces(jPlaces);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
            int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            /** Taking each place, parses and adds to list object */
            for (int i = 0; i < placesCount; i++) {
                try {
                    /** Call getPlace with place JSON object to parse the place */
                    place = getPlace((JSONObject) jPlaces.get(i));
                    placesList.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        /**
         * Parsing the Place JSON object
         */
        private HashMap<String, String> getPlace(JSONObject jPlace) {

            HashMap<String, String> place = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";
            String pic="";
            JSONArray jPic;
           // HashMap<String,String> pic= new HashMap<>();
            try {
                // Extracting Place name, if available
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Extracting Place Vicinity, if available
                if (!jPlace.isNull("vicinity")) {
                    vicinity = jPlace.getString("vicinity");
                }
                if(jPlace.has("photos"))
                {
                    jPic=jPlace.getJSONArray("photos");

                    pic= jPic.getJSONObject(0).getString("photo_reference");
                    Log.e("photo_ref",pic);
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = jPlace.getString("reference");

                place.put("place_name", placeName);
                place.put("vicinity", vicinity);
                place.put("lat", latitude);
                place.put("lng", longitude);
                place.put("reference", pic);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return place;
        }
    }
    private void ParserTask1(String[] jsonData){
        JSONObject jObject;
        List<HashMap<String, String>> places = null;
        Place_JSON placeJson = new Place_JSON();

        try {
            jObject = new JSONObject(jsonData[0]);

            places = placeJson.parse(jObject);

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        Log.d("Map", "list size: " + places.size());
        int len;
        if(places.size()<5)
            len=places.size();
        else
            len=4;

        for (int i = 6 ; i < (len+6) ; i++) {

            // Getting a place from the places list
            HashMap<String, String> hmPlace = places.get(i);

            // Getting name
            String name = hmPlace.get("place_name");
            photo_ref = hmPlace.get("reference");
            names.add(i,name);
            //Toast.makeText(SearchResults.this, photo_ref, Toast.LENGTH_SHORT).show();
            references.add(i-6,photo_ref);
            Log.e("name",name);
            Log.e("ref",photo_ref);



        }
        int i;
        for(i=0;i<names.size();i++)
        {
            //fetch photos
            //if(!references.equals("")) {
            StringBuilder photoSbValue = new StringBuilder(sbMethod("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference=" + references.get(i) + "&key=AIzaSyATJSb81r1r8JVWKhMH76clBsjfqKfzAxA"));
            PhotosTask photosTask = new PhotosTask();
            photosTask.execute(photoSbValue.toString());
            //PhotosTask1(new String[]{photoSbValue.toString()});
            //}
        }
            /*while(i<4){
                rl[i].setVisibility(View.GONE);
                i++;
            }*/
    }

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            Place_JSON placeJson = new Place_JSON();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = placeJson.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            Log.d("Map", "list size: " + list.size());
            int len;
            if(list.size()<5)
                len=list.size();
            else
                len=4;

            for (int i = 6 ; i < (len+6) ; i++) {

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);

                // Getting name
                String name = hmPlace.get("place_name");
                photo_ref = hmPlace.get("reference");
                names.add(name);
                //Toast.makeText(SearchResults.this, photo_ref, Toast.LENGTH_SHORT).show();
                references.add(photo_ref);
                Log.e("name",name);
                Log.e("ref",photo_ref);



            }
            fetchPics();
        }

        public void fetchPics()
        {
            int i;
           for(i=0;i<names.size();i++)
            {
                //fetch photos
                //if(!references.equals("")) {
                    StringBuilder photoSbValue = new StringBuilder(sbMethod("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&maxheight=400&photoreference=" + references.get(i) + "&key=AIzaSyB6IqSaObL6WhImbFfqfheVzbeN-rP0Sgc"));
                    PhotosTask photosTask = new PhotosTask();
                    photosTask.execute(photoSbValue.toString());
                //}
            }
            /*while(i<4){
                rl[i].setVisibility(View.GONE);
                i++;
            }*/
        }
    }

    private void PhotosTask1(String[] url){
        Bitmap data = null;
        try {
            data = downloadUrlPhotos(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        Drawable d = new BitmapDrawable(getResources(), data);
        im[count].setBackground(d);
        tv[count].setText(names.get(count));
        count++;
        Log.e("abc", "count="+count+"..url="+url[0]);
        //Toast.makeText(SearchResults.this, "count="+count+"..url="+url[0], Toast.LENGTH_SHORT).show();
    }

    private class PhotosTask extends AsyncTask<String, Integer, String> {

        Bitmap data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrlPhotos(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return "done";
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            Drawable d = new BitmapDrawable(getResources(), data);
            im[count].setBackground(d);
            tv[count].setText(names.get(count));
            count++;
        }
    }
    private Bitmap downloadUrlPhotos(String strUrl) throws IOException {

        InputStream iStream = null;
        Bitmap image=null;
        HttpURLConnection urlConnection = null;
        try {
            //Toast.makeText(SearchResults.this, "Step 1: Entered", Toast.LENGTH_SHORT).show();
            URL url = new URL(strUrl);
            //Toast.makeText(SearchResults.this, "Step 2: url", Toast.LENGTH_SHORT).show();
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            //Toast.makeText(SearchResults.this, "Step 3: url connection", Toast.LENGTH_SHORT).show();
            // Connecting to url
            urlConnection.connect();
            //Toast.makeText(SearchResults.this, "Step 4: connection made", Toast.LENGTH_SHORT).show();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            if(iStream.equals("")){
                //Toast.makeText(SearchResults.this, "null stream", Toast.LENGTH_SHORT).show();
            }
            //Toast.makeText(SearchResults.this, "Step 5", Toast.LENGTH_SHORT).show();
            image= BitmapFactory.decodeStream(iStream);
            //Toast.makeText(SearchResults.this, "Step 6", Toast.LENGTH_SHORT).show();
           // Bitmap photo = BitmapFactory.decodeStream(request.execute().getContent());
         /*   BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();*/

        } catch (Exception e) {
            Log.d("Downloading Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return image;

    }



}
