package com.vistar.flight.vistarainflightservices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;

    private String mAnswer;
    private int mLength = mQuestionLibrary.getNumberOfQuestions();
    private int mScore = 0;
    private int mQuestionNumber = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);

        updateQuestion();


        mButtonChoice1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice1.getText() == mAnswer){
                    mScore++;
                    updateScore(mScore);
                    Toast.makeText(QuizActivity.this,"correct",Toast.LENGTH_SHORT).show();
                    updateQuestion();

                }else{
                    Toast.makeText(QuizActivity.this,"wrong",Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }

            }
        });


        mButtonChoice2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice2.getText() == mAnswer){
                    mScore++;
                    updateScore(mScore);
                    Toast.makeText(QuizActivity.this,"correct",Toast.LENGTH_SHORT).show();
                    updateQuestion();

                }else{
                    Toast.makeText(QuizActivity.this,"wrong",Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }

            }
        });


        mButtonChoice3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                if (mButtonChoice3.getText() == mAnswer){
                    mScore++;
                    updateScore(mScore);
                    Toast.makeText(QuizActivity.this,"correct",Toast.LENGTH_SHORT).show();
                    updateQuestion();

                }else{
                    Toast.makeText(QuizActivity.this,"wrong",Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }

            }
        });


    }

    private void updateQuestion() {
        if (mQuestionNumber<mLength) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            mQuestionView.startAnimation(animation);
            mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));

            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;

        }
        else{
            String score= Integer.toString(mScore);
            Intent intent = new Intent(getApplicationContext(),score.class);
            intent.putExtra("score",score);
            startActivity(intent);
        }
    }
    private void updateScore(int point){
        mScoreView.setText("" + mScore);
    }
}
