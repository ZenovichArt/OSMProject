package com.cs.OSMProject;

import java.util.ArrayList;

public class GeoJSONParser {

    public static Boolean checkGeoJSON (String geoJSONdata) {
        if (geoJSONdata.length() > 100)
            return true;

        return false;
    }

    private static int GetStartingIndex (String geoJSONdata) {
        int startingIndex = -1;

            for (int i = 0; i < geoJSONdata.length() - 7; i++) {
                if (geoJSONdata.substring(i, i + 7).equals("geojson")) {
                    startingIndex = i;
                }
            }

            return startingIndex;
    }

    public static ArrayList<Double> ParseCoordinatesGeoJSON(String geoJSONdata) {
        ArrayList<Double> coordinates = new ArrayList<Double>();

        if (checkGeoJSON(geoJSONdata)) {

            int startingIndex = GetStartingIndex(geoJSONdata);
            int numberLength = 3;

            for (int i = startingIndex; i < geoJSONdata.length(); i++) {
                if (Character.isDigit(geoJSONdata.charAt(i))) {
                    while (Character.isDigit(geoJSONdata.charAt(i + numberLength))) {
                        numberLength++;
                    }
                    coordinates.add(Double.parseDouble(geoJSONdata.substring(i, i + numberLength)));
                    i += numberLength;
                    numberLength = 3;
                }
            }
        }

        return coordinates;
    }

}
