package com.cs.OSMProject;

import java.util.ArrayList;

public class StorageData {

    String name, type;
    ArrayList<Double> coordinates;
    ArrayList<Double> center;

    public StorageData (String name, String type, ArrayList<Double> coordinates, ArrayList<Double> center) {
        this.name = name;
        this.type = type;
        this.coordinates = coordinates;
        this.center = center;
    }

    public String getName () {
        return name;
    }

    public String getType () {
        return type;
    }

    public ArrayList<Double> getCoordinates () {
        return coordinates;
    }

    public ArrayList<Double> getCenter () {
        return center;
    }

    public void setName () {
        this.name = name;
    }

    public void setType () {
        this.type = type;
    }

    public void setCoordinates () {
        this.coordinates = coordinates;
    }

    public void setCenter () {
        this.center = center;
    }
}
