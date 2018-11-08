package com.lopez.julz.qrattendance;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DownloadClasses extends AppCompatActivity {

    public ListView listView;
    public FloatingActionButton download;
    public Toolbar toolbar;
    public TextView title;
    public APIParser parser;
    private ProgressDialog progress;
    public List<Classes> classesList;
    public DatabaseHelper db;

    public String semid = "";
    public String sem = "";
    public String tchnum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_classes);

        Bundle bundle = getIntent().getExtras();
        semid = bundle.getString("SEM_ID");
        sem = bundle.getString("SEM");
        tchnum = bundle.getString("TCHNUM");
        title = (TextView) findViewById(R.id.download_class_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar_downloadclass);
        setSupportActionBar(toolbar);
        title.setText(semid + " - " + sem);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);
        download = (FloatingActionButton) findViewById(R.id.download_classes_button);
        listView = (ListView) findViewById(R.id.class_list);
        parser = new APIParser();
        classesList = new ArrayList<>();
        
        new LoadClasses().execute(tchnum, semid);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.insertIntoSems(semid, sem);

                download(view, classesList);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    class LoadClasses extends AsyncTask<String, Void, Void> {

        ArrayAdapter<String> adapter;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            List<Classes> classes = parser.getClasses(strings[0], strings[1]);
            classesList = parser.getClasses(strings[0], strings[1]);
            int size = classes.size();
            if (size > 0) {
                String[] arr = new String[size];

                for (int i=0; i<size; i++) {
                    arr[i] = classes.get(i).getCourse() + " (" + classes.get(i).getDate() + " on " + classes.get(i).getRoom() + ")";
                }

                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arr);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            listView.setAdapter(adapter);
            super.onPostExecute(aVoid);
        }
    }

    public void download(View view, final List<Classes> classList){
        progress = new ProgressDialog(this);
        progress.setMessage("Downloading Classes");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.show();

        final int total = classList.size();

        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                db.deleteFromClasses(semid);

                while(jumpTime < total) {
                    try {
                        db.insertToClasses( classList.get(jumpTime).getId(), classList.get(jumpTime).getCourse(),
                                classList.get(jumpTime).getTimeStart(),
                                classList.get(jumpTime).getTimeEnd(),
                                classList.get(jumpTime).getDate(),
                                classList.get(jumpTime).getRoom(),
                                semid);
                        Log.e("t", jumpTime +"");
                        jumpTime += 1;

                        progress.setProgress(jumpTime);
                    } catch (Exception e) {
                        Log.e("ERR_DWNLD", e.getMessage());
                    }
                }

                db.getClasses(semid);
            }
        };
        t.start();
        progress.dismiss();
        Toast.makeText(DownloadClasses.this, "Class schedules successfully downloaded!", Toast.LENGTH_SHORT).show();
        finish();
    }

}
