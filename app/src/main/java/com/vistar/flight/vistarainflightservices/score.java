package com.vistar.flight.vistarainflightservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.SmileRating;

public class score extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        final RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        int score = Integer.parseInt(getIntent().getStringExtra("score"));

        tv =(TextView) findViewById(R.id.scorefinal);
        tv.setText("SCORE IS : "+getIntent().getStringExtra("score"));

        ratingBar.setRating(score);

        final SmileRating smileRating = (SmileRating) findViewById(R.id.smile_rating);
        smileRating.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected(int smiley, boolean reselected) {

                switch (smiley) {
                    case SmileRating.BAD:
                        Toast.makeText(getApplicationContext(),"Bad",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GOOD:
                        Toast.makeText(getApplicationContext(),"GOOD",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.GREAT:
                        Toast.makeText(getApplicationContext(),"GREAT",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.OKAY:
                        Toast.makeText(getApplicationContext(),"OKAY",Toast.LENGTH_SHORT).show();
                        break;
                    case SmileRating.TERRIBLE:
                        Toast.makeText(getApplicationContext(),"TERRIBLE",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


    }
}
