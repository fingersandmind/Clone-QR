package com.lopez.julz.qrattendance;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class Login extends AppCompatActivity {

    public EditText username, password;
    public Button login;

    public AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString();
                String pword = password.getText().toString();
                new LoginThred().execute(uname, pword);
            }
        });
    }

    public void showProgressBar() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = LayoutInflater.from(this);
            View progressView = inflater.inflate(R.layout.login_progress, null);
            builder.setView(progressView);

            ProgressBar progressBar = (ProgressBar) progressView.findViewById(R.id.login_progress);

            dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();
        } catch (Exception e) {
            Log.e("ERR_LOADING_PROGRESS", e.getMessage());
        }
    }

    class LoginThred extends AsyncTask<String, Void, String> {

        String errorResponse = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressBar();
        }

        @Override
        protected String doInBackground(String... strings) {
            OutputStream os = null;
            HttpURLConnection conn = null;
            try {
                //constants
                URL url = new URL("http://192.168.10.170/janrey_api/login.php");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("usr", strings[0]);
                jsonObject.put("pwd", strings[1]);
                String message = jsonObject.toString();

                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout( 10000 /*milliseconds*/ );
                conn.setConnectTimeout( 15000 /* milliseconds */ );
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setFixedLengthStreamingMode(message.getBytes().length);

                //make some HTTP header nicety
                conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

                //open
                conn.connect();

                //setup send
                os = new BufferedOutputStream(conn.getOutputStream());
                os.write(message.getBytes());
                //clean up
                os.flush();
                os.close();
                //do somehting with response
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String line = "";
                    ArrayList<String> sb = new ArrayList<>();

                    while((line = br.readLine()) != null) {
                        if (line.length() > 1) {
                            sb.add(line);
                            break;
                        }
                    }

                    String resCodes[] = sb.get(0).split(": ");
                    String resCode = resCodes[0].replaceAll("\"","");

                    br.close();
                    return resCode.trim();
                } else {
                    errorResponse = "Error connecting to the server. \nResponse code " + responseCode;
                    return errorResponse;
                }

            } catch (IOException e) {
                Log.e("IO_EXCEPTION", e.getMessage());
                return null;
            } catch (JSONException e) {
                Log.e("JSON_EXCEPTION", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals("200")) { // IF LOGIN IS SUCCESSFUL
                Bundle bundle = new Bundle();
                bundle.putString("username", username.getText().toString());
                Intent intent = new Intent(Login.this, Home.class);
                startActivity(intent, bundle);
            } else { // IF NOT
                Snackbar.make(login, "User not found.", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

}
