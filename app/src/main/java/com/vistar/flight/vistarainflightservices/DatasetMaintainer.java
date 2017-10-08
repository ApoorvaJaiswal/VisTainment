package com.vistar.flight.vistarainflightservices;

import android.content.Context;

import java.util.ArrayList;

public class DatasetMaintainer{
    static Context context;
    static DatasetMaintainer datasetMaintainer = null;
    ArrayList<Datasets> datasets = new ArrayList<>();
    public static DatasetMaintainer getInstance(Context contex){
        context = contex;
        if(datasetMaintainer == null){
            //datasets = new Datasets();
            datasetMaintainer = new DatasetMaintainer();
            return datasetMaintainer;
        }
        else {
            return datasetMaintainer;
        }
    }
    public void newEntry(Datasets datasets1){
        datasets.add(datasets1);
    }
    public ArrayList<Datasets> getFullDataset(){
        return datasets;
    }

}
