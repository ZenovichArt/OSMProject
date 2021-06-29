package com.cs.OSMProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestURLMaker {

    public static String getURLJPanel (String name, String type) {

        String url = "https://nominatim.openstreetmap.org/search?" + type.toLowerCase() + "=" + name.toLowerCase() + "&format=json&polygon_geojson=1";

        return url;
    }

    public static String getURL () throws IOException {

        String url;

        String name = getName();
        String type = getType();

        url = "https://nominatim.openstreetmap.org/search?" + type.toLowerCase() + "=" + name + "&format=json&polygon_geojson=1";

        //String example1 = "http://nominatim.openstreetmap.org/search?q=ПФО&country=russia&format=json&polygon_geojson=1";
        //String example2 = "https://nominatim.openstreetmap.org/search?state=Самарская%20область&country=russia&format=json&polygon_geojson=1";


        return url;
    }

    private static String getName () throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter name:");
        String urlRequest = reader.readLine();

        return urlRequest.toString();
    }

    private static String getType () throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter type:");
        String urlRequest = reader.readLine();

        return urlRequest.toString();
    }
}
