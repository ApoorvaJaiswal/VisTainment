package com.vistar.flight.vistarainflightservices;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOfChoices extends AppCompatActivity {
    ChoicesNode gameOfChoices;
    TextView questionStory, choiceStoryLeft, choiceStoryRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_of_choices);

        gameOfChoices = createTheGame();

        questionStory = (TextView)findViewById(R.id.questionStory);
        choiceStoryLeft = (TextView)findViewById(R.id.choiceStoryLeft);
        choiceStoryRight = (TextView)findViewById(R.id.choiceStoryRight);

        questionStory.setText(gameOfChoices.question);
        choiceStoryLeft.setText(gameOfChoices.leftOption);
        choiceStoryRight.setText(gameOfChoices.rightOption);

        choiceStoryLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOfChoices = gameOfChoices.leftChoice;
                questionStory.setText(gameOfChoices.question);
                if(gameOfChoices.leftChoice == null && gameOfChoices.rightChoice == null){
                    choiceStoryLeft.setVisibility(View.GONE);
                    choiceStoryRight.setVisibility(View.GONE);
                }
                else {
                    choiceStoryLeft.setText(gameOfChoices.leftOption);
                    choiceStoryRight.setText(gameOfChoices.rightOption);
                }
            }
        });
        choiceStoryRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOfChoices = gameOfChoices.rightChoice;
                questionStory.setText(gameOfChoices.question);
                if(gameOfChoices.leftChoice == null && gameOfChoices.rightChoice == null){
                    choiceStoryLeft.setVisibility(View.GONE);
                    choiceStoryRight.setVisibility(View.GONE);
                }
                else {
                    choiceStoryLeft.setText(gameOfChoices.leftOption);
                    choiceStoryRight.setText(gameOfChoices.rightOption);
                }
            }
        });
    }

    public static ChoicesNode createTheGame(){
        ChoicesNode root1;
        ChoicesNode root = new ChoicesNode("You are sitting in a park, on a bench. What will you do next?", "Leave the park", "Continue sitting");
        root1 = root;
        ChoicesNode left = new ChoicesNode("You saw an accident on your way. What next?", "You took photos of the accident", "You took the person to the hospital");
        ChoicesNode right = new ChoicesNode("You see a child fall on the ground while running, and no one is comin for help", "You go help him", "Ignore him");
        root.leftChoice = left;
        root.rightChoice = right;
        root = left;
        ChoicesNode temp = right;

        left = new ChoicesNode("You too met with an accident", null, null);
        right = new ChoicesNode("The person's family members reach the hospital. You explain the situation to them, and leave", null, null);
        root.leftChoice = left;
        root.rightChoice = right;
        root = temp;

        left = new ChoicesNode("You found he was not hurt much. Next?", "You take him to a toffee shop and buy some for him", "You leave him and decide to go home");
        right = new ChoicesNode("You did what you always do, as you are fed up with your life. Nothing changes in your life", null, null);
        root.leftChoice = left;
        root.rightChoice = right;
        root = left;

        left = new ChoicesNode("You met the child's mother. Seeing your kindness, she invites you to join them to the nearby zoo", "You go along", "You reject the invitation");
        right = new ChoicesNode("You found your old friend on your way back", "You take him to a party", "You take him to your home");
        root.leftChoice = left;
        root.rightChoice = right;
        root = left;
        temp = right;

        left = new ChoicesNode("You enjoyed your day, missed your childhood days and your mother. You reavh your home and call your mother", null, null);
        right = new ChoicesNode("You wanted to enjoy but smirked at your busy life", null, null);
        root.leftChoice = left;
        root.rightChoice = right;
        root = temp;

        left = new ChoicesNode("You started singing on stage, and got to know your friend's new pub", null, null);
        right = new ChoicesNode("You made your friend meet your wife and other family memebers, enjoyed yourself, and served your friend a good home-made food", null, null);
        root.leftChoice = left;
        root.rightChoice = right;

        return root1;
    }

    public static class ChoicesNode{
        public String question,leftOption,rightOption;
        public ChoicesNode leftChoice,rightChoice;
        public ChoicesNode(String question, String leftOption, String rightOption){
            this.question = question;
            this.leftOption = leftOption;
            this.rightOption = rightOption;
        }
    }
}
