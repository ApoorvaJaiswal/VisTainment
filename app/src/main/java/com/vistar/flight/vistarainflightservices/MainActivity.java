package com.vistar.flight.vistarainflightservices;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button discoverDest;
    private DatabaseReference myRef;
    public final String TAG = "abc";
    String pnr,name;
    EditText et;
    String Name,Contact,Email,Gender,DOJ,FlightNo,SeatNo,Source,Destination,BoardingTime,Depart,Arrive,Social,PoolLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.et1);
    }
    public void DB(){
        myRef = FirebaseDatabase.getInstance().getReference();

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Name = dataSnapshot.child("Passengers").child(pnr).child("Name").getValue(String.class);
                Contact = dataSnapshot.child("Passengers").child(pnr).child("Contact").getValue(String.class);
                Email = dataSnapshot.child("Passengers").child(pnr).child("Email").getValue(String.class);
                Gender = dataSnapshot.child("Passengers").child(pnr).child("Gender").getValue(String.class);
                DOJ = dataSnapshot.child("Passengers").child(pnr).child("DOJ").getValue(String.class);
                FlightNo = dataSnapshot.child("Passengers").child(pnr).child("FlightNo").getValue(String.class);
                SeatNo = dataSnapshot.child("Passengers").child(pnr).child("SeatNo").getValue(String.class);
                Source = dataSnapshot.child("Passengers").child(pnr).child("Source").getValue(String.class);
                Destination = dataSnapshot.child("Passengers").child(pnr).child("Destination").getValue(String.class);
                BoardingTime = dataSnapshot.child("Passengers").child(pnr).child("BoardingTime").getValue(String.class);
                Depart = dataSnapshot.child("Passengers").child(pnr).child("Depart").getValue(String.class);
                Arrive = dataSnapshot.child("Passengers").child(pnr).child("Arrive").getValue(String.class);
                Social = dataSnapshot.child("Passengers").child(pnr).child("Social").getValue(String.class);
                PoolLoc = dataSnapshot.child("Passengers").child(pnr).child("PoolLoc").getValue(String.class);
                save();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public void save() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", Name);
        editor.putString("Contact", Contact);
        editor.putString("Email", Email);
        editor.putString("Gender", Gender);
        editor.putString("DOJ", DOJ);
        editor.putString("FlightNo", FlightNo);
        editor.putString("SeatNo", SeatNo);
        editor.putString("Source", Source);
        editor.putString("Destination", Destination);
        editor.putString("BoardingTime", BoardingTime);
        editor.putString("Depart", Depart);
        editor.putString("Arrive", Arrive);
        editor.putString("Social", Social);
        editor.putString("PoolLoc", PoolLoc);
        editor.putString("pnr", pnr);
        editor.commit();
    }

    public void user(View view) {
        pnr = et.getText().toString();
        DB();
        Intent intent = new Intent(MainActivity.this, user.class);
        startActivity(intent);
        //Toast.makeText(MainActivity.this, Name, Toast.LENGTH_SHORT).show();
        finish();
    }

}
