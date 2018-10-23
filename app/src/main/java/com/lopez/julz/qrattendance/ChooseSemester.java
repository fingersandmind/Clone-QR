package com.lopez.julz.qrattendance;

import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChooseSemester extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SemesterAdapter adapter;
    private List<Semester> semesterList;
    public APIParser parser;
    public Toolbar toolbar;

    public String tchnum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_semester);

        Bundle bundle = getIntent().getExtras();
        tchnum = bundle.getString("TCHNUM");

        toolbar = (Toolbar) findViewById(R.id.toolbar_choose_sem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        parser = new APIParser();
        recyclerView = (RecyclerView) findViewById(R.id.semester_recycler_view);
        semesterList = new ArrayList<>();
        adapter = new SemesterAdapter(this, semesterList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), 0);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(divider);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        new LoadSems().execute();

    }

    class LoadSems extends AsyncTask<Void, Void, Void> {

        List<Semester> sems;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            sems = parser.getSemesters();
            for (int i=0; i<sems.size(); i++) {
                semesterList.add(new Semester(sems.get(i).getSem(), sems.get(i).getSemid(), tchnum));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }
}
