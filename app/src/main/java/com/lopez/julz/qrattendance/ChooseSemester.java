package com.lopez.julz.qrattendance;

import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChooseSemester extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SemesterAdapter adapter;
    private List<Semester> semesterList;
    public APIParser parser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_semester);

        parser = new APIParser();
        recyclerView = (RecyclerView) findViewById(R.id.semester_recycler_view);
        semesterList = new ArrayList<>();
        adapter = new SemesterAdapter(this, semesterList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
       // DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), 0);
        recyclerView.setLayoutManager(mLayoutManager);
       // recyclerView.addItemDecoration(divider);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        loadSems();
    }

    public void loadSems() {
        try {
            List<Semester> sems = parser.getSemesters();

            for (int i=0; i<sems.size(); i++) {
                Semester s = new Semester(sems.get(i).getSem(), sems.get(i).getSemid());
                semesterList.add(s);
            }

            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("ERR_LOAD_SEM", e.getMessage());
        }
    }


}
