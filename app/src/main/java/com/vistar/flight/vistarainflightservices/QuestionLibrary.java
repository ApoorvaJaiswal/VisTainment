package com.vistar.flight.vistarainflightservices;


public class QuestionLibrary {

    private String mQuestions[] = {
            "Do you know when was vistara founded?",
            "Do you know vistara is a joint venture between ______ and ______.",
            "Guess the company slogan of vistara?",
            "How many Business class seats are there in vistara's Airbus A320-200 ?"

    };

    private String mChoices[][] = {
            {"2015","2013","2014"},
            {"Reliance & malasiya Airlines","Tata Sons & Singapore Airlines","Tata motors & Australia Airlines"},
            {"Fly the new feeling","The joy of flying","Air India...Truly Indian"},
            {"6","4","8"}

    };

    private String mCorrectAnswers[] = {"2013","Tata Sons & Singapore Airlines","Fly the new feeling","8"};

    public String getQuestion(int a){
        String question = mQuestions[a];
        return question;
    }

    public String getChoice1(int a){
        String choice0 = mChoices[a][0];
        return choice0;
    }

    public String getChoice2(int a){
        String choice1 = mChoices[a][1];
        return choice1;
    }

    public String getChoice3(int a){
        String choice2 = mChoices[a][2];
        return choice2;
    }

    public String getCorrectAnswer(int a){
        String answer = mCorrectAnswers[a];
        return answer;
    }

    public int getNumberOfQuestions(){
        int len = mQuestions.length;
        return len;
    }



}
