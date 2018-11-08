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

    public static String URL = "http://192.168.8.101/janrey_api/";
    public static String SEMS = "semesters.php";
    public static String CLASSES = "attendance.php";
    public static String INSERT_ATTENDANCE = "insert_attendance.php";

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
                sems.add(new Semester(items.getString("semester"), items.getString("id")));
            }

            return sems;
        } catch (Exception e) {
            Log.e("ERR_GETTING_SEM", e.getMessage());
            return null;
        }
    }

    public List<Classes> getClasses(String tchnum, String semid) {
        try {
            List<Classes> classes = new ArrayList<>();

            URL obj = new URL(URL + CLASSES + "?tchnum=" + tchnum + "&sem=" + semid);
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
                classes.add(new Classes(items.getString("id"),
                                        items.getString("course"),
                                        items.getString("time_start"),
                                        items.getString("time_end"),
                                        items.getString("day"),
                                        items.getString("room")
                        ));
            }

            return classes;
        } catch (Exception e) {
            Log.e("ERR_LOAD_CLASS", e.getMessage());
            return null;
        }
    }

    public boolean insertAttendance(String tcid, String date, String tin, String nature) {
        try {

            URL obj = new URL(URL + INSERT_ATTENDANCE + "?tcid=" + tcid + "&date=" + date + "&tin=" + tin + "&nature=" + nature);
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
            //response.toString().replace("[", "").replace("]", "").trim();
            System.out.println(response.toString());
            in.close();

            JSONObject myResponse = new JSONObject(response.toString());

            if (myResponse.getString("res").equals("200")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
