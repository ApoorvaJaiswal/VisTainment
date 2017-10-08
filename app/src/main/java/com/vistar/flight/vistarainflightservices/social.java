package com.vistar.flight.vistarainflightservices;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class social extends AppCompatActivity {

    private DatabaseReference myRef;
    public final String TAG = "abc";
    Switch aSwitch;
    String Social, pnr;
    TextView tv1, tv2, tv3, tv4;
    ArrayList<String> users = new ArrayList<>();
    ArrayList<String> nums = new ArrayList<>();
    ArrayList<String> pnrc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        aSwitch = (Switch) findViewById(R.id.switch1);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
        tv3.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = tv1.getText().toString();
                if (!chat.isEmpty()) {
                    Intent intent = new Intent(social.this, chat.class);
                    intent.putExtra("name", chat);
                    intent.putExtra("num", nums.get(0));
                    intent.putExtra("pnr", pnrc.get(0));
                    startActivity(intent);
                }
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = tv2.getText().toString();
                if (!chat.isEmpty()) {
                    Intent intent = new Intent(social.this, chat.class);
                    intent.putExtra("name", chat);
                    intent.putExtra("num", nums.get(1));
                    intent.putExtra("pnr", pnrc.get(1));
                    startActivity(intent);
                }
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = tv3.getText().toString();
                if (!chat.isEmpty()) {
                    Intent intent = new Intent(social.this, chat.class);
                    intent.putExtra("name", chat);
                    intent.putExtra("num", nums.get(2));
                    intent.putExtra("pnr", pnrc.get(2));
                    startActivity(intent);
                }
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chat = tv4.getText().toString();
                if (!chat.isEmpty()) {
                    Intent intent = new Intent(social.this, chat.class);
                    intent.putExtra("name", chat);
                    intent.putExtra("num", nums.get(3));
                    intent.putExtra("pnr", pnrc.get(3));
                    startActivity(intent);
                }
            }
        });

        load();
        //Toast.makeText(social.this, Social, Toast.LENGTH_SHORT).show();
        if (Social.equals("yes")) {
            aSwitch.setChecked(true);
        } else if (Social.equals("no")) {
            aSwitch.setChecked(false);
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                write(isChecked);
            }
        });
        update();
    }

    private void update() {
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                users.clear();
                nums.clear();
                pnrc.clear();
                tv1.setText("");
                tv2.setText("");
                tv3.setText("");
                tv4.setText("");
                tv1.setVisibility(View.GONE);
                tv2.setVisibility(View.GONE);
                tv3.setVisibility(View.GONE);
                tv4.setVisibility(View.GONE);
                for (DataSnapshot childSnapshot : dataSnapshot.child("Passengers").getChildren()) {
                    String pnrt = childSnapshot.getKey();
                    String check = dataSnapshot.child("Passengers").child(pnrt).child("Social").getValue(String.class);
                    String name = dataSnapshot.child("Passengers").child(pnrt).child("Name").getValue(String.class);
                    String no = dataSnapshot.child("Passengers").child(pnrt).child("Contact").getValue(String.class);
                    if (aSwitch.isChecked())
                        if (check.equals("yes")) {
                            if (!pnrt.equals(pnr)) {
                                //Toast.makeText(social.this, pnrt, Toast.LENGTH_SHORT).show();
                                users.add(name);
                                nums.add(no);
                                pnrc.add(pnrt);
                            }

                            //Toast.makeText(social.this, name, Toast.LENGTH_SHORT).show();
                        }
                    //Toast.makeText(social.this, pnr, Toast.LENGTH_SHORT).show();
                }
                if (users.size() >= 1) {
                    tv1.setText(users.get(0));
                    tv1.setVisibility(View.VISIBLE);
                }
                if (users.size() >= 2) {
                    tv2.setText(users.get(1));
                    tv2.setVisibility(View.VISIBLE);
                }
                if (users.size() >= 3) {
                    tv3.setText(users.get(2));
                    tv3.setVisibility(View.VISIBLE);
                }
                if (users.size() >= 4) {
                    tv4.setText(users.get(3));
                    tv1.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void write(boolean value) {
        myRef = FirebaseDatabase.getInstance().getReference();
        if (value) {
            myRef.child("Passengers").child(pnr).child("Social").setValue("yes");
        } else
            myRef.child("Passengers").child(pnr).child("Social").setValue("no");
    }

    public void load() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        Social = sharedPreferences.getString("Social", Social);
        pnr = sharedPreferences.getString("pnr", pnr);
    }
}
