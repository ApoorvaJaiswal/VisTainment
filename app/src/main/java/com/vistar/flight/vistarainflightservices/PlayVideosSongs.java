package com.vistar.flight.vistarainflightservices;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Ashish on 10/8/2017.
 */

public class PlayVideosSongs extends Activity {

    int c=0;
    TextView tv[]= new TextView[7];
    String val="";
    Intent iv;
    MediaPlayer mediaPlayer;
    HashMap<String,String> im= new HashMap<>();

    Button b,b1,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the layout from video_main.xml
        setContentView(R.layout.movies_songs);
        Log.e("entered","ghg");
        iv=new Intent(this,VideoViewActivity.class);
        mediaPlayer= new MediaPlayer();
        tv[0]=(TextView)findViewById(R.id.tv1);
        tv[1]=(TextView)findViewById(R.id.tv12);
        tv[2]=(TextView)findViewById(R.id.tv11);
        tv[3]=(TextView)findViewById(R.id.tv2);
        tv[4]=(TextView)findViewById(R.id.tv21);
        tv[5]=(TextView)findViewById(R.id.tv22);
        tv[6]=(TextView)findViewById(R.id.tv23);
        b=(Button)findViewById(R.id.b1);
        b1=(Button)findViewById(R.id.b2);

        p5=(Button)findViewById(R.id.p5);
        p6=(Button)findViewById(R.id.p6);
        p7=(Button)findViewById(R.id.p7);
        p8=(Button)findViewById(R.id.p8);
        p9=(Button)findViewById(R.id.p9);
        p10=(Button)findViewById(R.id.p10);
        //assign names of movies to tv12,tv11 and songs to tv21,22,23 from db
        im.put("ML-1","http://res.cloudinary.com/dirnauajx/video/upload/v1507411508/Section_1_-_2_yupctf.mp4");
        im.put("ML-2","http://res.cloudinary.com/dirnauajx/video/upload/v1507412911/Section_1_-_4_wunp80.mp4");
        im.put("Besabriyan","http://res.cloudinary.com/dirnauajx/video/upload/v1507414794/01_-_Besabriyaan_MS_Dhoni_Songspk.GURU_vo9kho.mp3");
        im.put("Shape of You","http://res.cloudinary.com/dirnauajx/video/upload/v1507413097/Shape_of_You-Songspksongspks.Com_ladr0b.mp3");
        im.put("The Greatest","http://res.cloudinary.com/dirnauajx/video/upload/v1507413185/Sia_-_The_Greatest_avmsg1.mp3");

        tv[1].setText("ML-1");
        tv[2].setText("ML-2");
        tv[4].setText("Besabriyaan");
        tv[5].setText("Shape of You");
        tv[6].setText("The Greatest");

        tv[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.putExtra("url",im.get("ML-1"));
                startActivity(iv);
            }
        });
        tv[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iv.putExtra("url",im.get("ML-2"));
                startActivity(iv);
            }
        });
        p6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.setDataSource(im.get("Besabriyaan"));
                    mediaPlayer.prepareAsync();
                }
                catch(Exception ex)
                {

                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        p6.setVisibility(View.GONE);
                        p5.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        p8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer.setDataSource(im.get("Shape of You"));
                    mediaPlayer.prepareAsync();
                }
                catch(Exception ex)
                {

                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        p8.setVisibility(View.GONE);
                        p7.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        p10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    mediaPlayer.setDataSource(im.get("The Greatest"));
                    mediaPlayer.prepareAsync();
                }
                catch(Exception ex)
                {

                }
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        p10.setVisibility(View.GONE);
                        p9.setVisibility(View.VISIBLE);
                    }
                });

            }
        });
        p5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                p5.setVisibility(View.GONE);
                p6.setVisibility(View.VISIBLE);
            }
        });
        p7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                p7.setVisibility(View.GONE);
                p8.setVisibility(View.VISIBLE);
            }
        });
        p9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                p9.setVisibility(View.GONE);
                p10.setVisibility(View.VISIBLE);
            }
        });

    }

}
