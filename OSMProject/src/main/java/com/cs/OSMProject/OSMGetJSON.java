package com.cs.OSMProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OSMGetJSON {

    private static HttpURLConnection connection;

    public static String OSMGetJSONdata (String urlRequest) {

            BufferedReader reader;
            String line;
            StringBuffer geoJSONdata = new StringBuffer();

            try {
                URL url = new URL(urlRequest);
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);

                int status = connection.getResponseCode();
                //System.out.println(status);

                if (status > 400) {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while ((line = reader.readLine()) != null) {
                        geoJSONdata.append(line);
                    }
                    reader.close();
                } else {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        geoJSONdata.append(line);
                    }
                    reader.close();
                }

                //System.out.println(geoJSONdata.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }

            return geoJSONdata.toString();
    }

}
