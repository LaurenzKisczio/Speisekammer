package com.example.myfirstapplication;

import android.app.Application;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Speisekammer extends Application {
    private String bdb = "bdb.txt";
    private ArrayList<String[]> database;

    public ArrayList<String[]> getDatabase() throws IOException {
        if(database == null){
            initialiseDatabase();
        }
        return database;
    }
    public void initialiseDatabase() throws IOException {
        Scanner scanner = new Scanner(getResources().getAssets().open(bdb));
        database = new ArrayList<String[]>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            database.add(line.split(";"));
        }
    }
}
