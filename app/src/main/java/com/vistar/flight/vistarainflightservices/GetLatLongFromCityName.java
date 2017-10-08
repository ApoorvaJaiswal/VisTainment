package com.vistar.flight.vistarainflightservices;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Apoorva Jaiswal on 10/7/2017.
 */

public class GetLatLongFromCityName extends Activity{

    Button restaurant,lodging,museum,amusement_park,hospital,shopping_mall;
    String mLocation;
    String typeToSearch="restaurant";
    int type1=0,type2=0,type3=0,type4=0,type5=0,type6=0;
    TextView placeQuestion;
    String choices[] = new String[7];
    Intent i;
    int currentChoiceWillBe=0,flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_lat_lang_from_city_name);

        for(int i=1; i<=6; i++){
            choices[i] = null;
        }
        type1 = getIntent().getIntExtra("type1", 0);
        type2 = getIntent().getIntExtra("type2", 0);
        type3 = getIntent().getIntExtra("type3", 0);
        type4 = getIntent().getIntExtra("type4", 0);
        type5 = getIntent().getIntExtra("type5", 0);
        type6 = getIntent().getIntExtra("type6", 0);
        for(int i=1; i<=6; i++){
            choices[i] = getIntent().getExtras().getString("choice"+i);
            if(choices[i] == null && flag == 0){
                currentChoiceWillBe = i;
                flag = 1;
            }
        }

        restaurant=(Button)findViewById(R.id.restaurant);
        lodging= (Button)findViewById(R.id.lodging);
        museum= (Button)findViewById(R.id.museum);
        amusement_park= (Button)findViewById(R.id.amusement_park);
        hospital= (Button)findViewById(R.id.hospital);
        shopping_mall= (Button)findViewById(R.id.shopping_mall);

        //fetch latitude and longitude from previous activity

        mLocation=getIntent().getExtras().getString("place");

        placeQuestion = (TextView)findViewById(R.id.placeQuestion);
        placeQuestion.setText("What do you wanna do when you reach "+mLocation);

        if(type1 == 1) {
            restaurant.setVisibility(View.GONE);
            placeQuestion.setText("What do you wanna do next");
        }
        if(type2 == 1) {
            lodging.setVisibility(View.GONE);
            placeQuestion.setText("What do you wanna do next");
        }
        if(type3 == 1) {
            museum.setVisibility(View.GONE);
            placeQuestion.setText("What do you wanna do next");
        }
        if(type4 == 1) {
            amusement_park.setVisibility(View.GONE);
            placeQuestion.setText("What do you wanna do next");
        }
        if(type5 == 1) {
            hospital.setVisibility(View.GONE);
            placeQuestion.setText("What do you wanna do next");
        }
        if(type6 == 1) {
            shopping_mall.setVisibility(View.GONE);
            placeQuestion.setText("What do you wanna do next");
        }

        if(currentChoiceWillBe > 6){
            placeQuestion.setText("That's it.. Hope you have a great and safe journey");
            DatasetMaintainer datasetMaintainer = DatasetMaintainer.getInstance(GetLatLongFromCityName.this);
            String choicess[] = new String[6];
            for(int i=1; i<=6; i++){
                choicess[i] = choices[i];
            }
            Datasets entry = new Datasets("Student", "Kolkata", "7:00", choicess);
            datasetMaintainer.newEntry(entry);

            for (Datasets d:datasetMaintainer.getFullDataset()){
                Toast.makeText(GetLatLongFromCityName.this, d.typeOfTraveller, Toast.LENGTH_SHORT).show();
            }
        }

        i= new Intent(getApplicationContext(),SearchResults.class);
        i.putExtra("place",mLocation);
        restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("type1",1);
                i.putExtra("type2",type2);
                i.putExtra("type3",type3);
                i.putExtra("type4",type4);
                i.putExtra("type5",type5);
                i.putExtra("type6",type6);
                i.putExtra("choice"+currentChoiceWillBe, "restaurant");
                i.putExtra("type","restaurant");
                startActivity(i);
            }
        });
        lodging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("type1",type1);
                i.putExtra("type2",1);
                i.putExtra("type3",type3);
                i.putExtra("type4",type4);
                i.putExtra("type5",type5);
                i.putExtra("type6",type6);
                i.putExtra("choice"+currentChoiceWillBe, "lodging");
                i.putExtra("type","lodging");
                startActivity(i);
            }
        });
        museum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("type1",type1);
                i.putExtra("type2",type2);
                i.putExtra("type3",1);
                i.putExtra("type4",type4);
                i.putExtra("type5",type5);
                i.putExtra("type6",type6);
                i.putExtra("choice"+currentChoiceWillBe, "museum");
                i.putExtra("type","museum");
                startActivity(i);
            }
        });
        amusement_park.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("type1",type1);
                i.putExtra("type2",type2);
                i.putExtra("type3",type3);
                i.putExtra("type4",1);
                i.putExtra("type5",type5);
                i.putExtra("type6",type6);
                i.putExtra("choice"+currentChoiceWillBe, "amusement_park");
                i.putExtra("type","amusement_park");
                startActivity(i);
            }
        });
        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("type1",type1);
                i.putExtra("type2",type2);
                i.putExtra("type3",type3);
                i.putExtra("type4",type4);
                i.putExtra("type5",1);
                i.putExtra("type6",type6);
                i.putExtra("choice"+currentChoiceWillBe, "hospital");
                i.putExtra("type","hospital");
                startActivity(i);
            }
        });
        shopping_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("type1",type1);
                i.putExtra("type2",type2);
                i.putExtra("type3",type3);
                i.putExtra("type4",type4);
                i.putExtra("type5",type5);
                i.putExtra("type6",1);
                i.putExtra("choice"+currentChoiceWillBe, "shopping_mall");
                i.putExtra("type","shopping_mall");
                startActivity(i);
            }
        });

    }


}
