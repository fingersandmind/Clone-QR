package com.lopez.julz.qrattendance;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;

/**
 * Created by jlopez on 10/22/2018.
 */

public class APIParser {

    public static String URL = "http://192.168.10.170/janrey_api/";
    public static String SEMS = "semesters.php";

    public APIParser() {}

    public List<Semester> getSemesters() {
        try {
            List<Semester> sems = new ArrayList<>();

            URL obj = new URL(URL + SEMS);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            response.toString().replace("[", "").replace("]", "").trim();
            in.close();
            JSONObject myResponse = new JSONObject(response.toString());

            int respLength = myResponse.length();

            for (int i=0; i<respLength; i++) {
                JSONObject items = myResponse.getJSONObject("" + i);
                sems.add(new Semester(items.getString("sem"), items.getString("sem_code")));
            }

            return sems;
        } catch (Exception e) {
            Log.e("ERR_GETTING_SEM", e.getMessage());
            return null;
        }
    }

}
