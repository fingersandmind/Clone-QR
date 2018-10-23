package com.lopez.julz.qrattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ChooseClass extends AppCompatActivity {

    public Toolbar toolbar;

    private RecyclerView recyclerView;
    private ClassesAdapter adapter;
    private List<Classes> classesList;
    public DatabaseHelper db;

    public Button chooseClass;
    public Spinner classesBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_class);

        toolbar = (Toolbar) findViewById(R.id.toolbar_choose_class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = new DatabaseHelper(this);
        recyclerView = (RecyclerView) findViewById(R.id.choose_classes_recycler_view);
        classesList = new ArrayList<>();
        adapter = new ClassesAdapter(this, classesList);
        classesBox = (Spinner) findViewById(R.id.sem_selector);
        chooseClass = (Button) findViewById(R.id.search_classes_btn);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        populateSpinner();
        chooseClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String semid = db.getSem(classesBox.getSelectedItem().toString()).getSemid();
                Log.e("OUT", semid+"");
                populateClasses(semid);
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

    public void populateClasses(String semid) {
        try {
            List<Classes> tmp = db.getClasses(semid);
            classesList.clear();

            int size = tmp.size();

            for (int i=0; i<size;i++) {
                classesList.add(new Classes(tmp.get(i).getCourse(), tmp.get(i).getTimeStart(), tmp.get(i).getTimeEnd(), tmp.get(i).getDate(), tmp.get(i).getRoom()));
            }

            adapter.notifyDataSetChanged();
        }catch (Exception e) {
            Log.e("ERR_LD_CLASS", e.getMessage());
        }
    }

    public void populateSpinner() {
        try {
            List<Semester> sems = new ArrayList<>();
            sems = db.getSems();

            List<String> toSpinnerValues = new ArrayList<>();
            for (int i=0; i<sems.size(); i++) {
                toSpinnerValues.add(sems.get(i).getSem());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, toSpinnerValues);
            classesBox.setAdapter(adapter);
        } catch ( Exception e ){
            Log.e("ERR_PPLT_SPINNER", e.getMessage());
        }
    }
}
