package com.cs.OSMProject;

import java.util.ArrayList;

public class Calculations {

    public static ArrayList<Double> getCenter (ArrayList<Double> coordinates) {
        ArrayList<Double> center = new ArrayList<Double>();

        Double longitudeSum = 0.0, latitudeSum = 0.0, amount = 0.0;

        for (int i = 0; i < coordinates.size(); i += 2) {
            longitudeSum += coordinates.get(i);
            latitudeSum += coordinates.get(i + 1);
            amount++;
        }

        center.add(longitudeSum/amount);
        center.add(latitudeSum/amount);

        return center;
    }

}
