package com.vistar.flight.vistarainflightservices;

public class Datasets {
    public String typeOfTraveller, destination, timeReached;
    public String activities[] = new String[6];

        public Datasets(String type, String dest, String timeRched, String[] activities){
            typeOfTraveller = type;
            destination = dest;
            timeReached = timeRched;
            this.activities = activities;
        }
}
