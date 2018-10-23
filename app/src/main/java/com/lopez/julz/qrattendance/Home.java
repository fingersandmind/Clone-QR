package com.lopez.julz.qrattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MenuAdapter adapter;
    private List<Menu> menuList;

    public String tchnum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        Bundle bundle = getIntent().getExtras();
        tchnum = "009";

        recyclerView = (RecyclerView) findViewById(R.id.menu_recycler_view);
        menuList = new ArrayList<>();
        adapter = new MenuAdapter(this, menuList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareMenus();
    }

    private void prepareMenus() {
        int[] covers = new int[]{
                R.drawable.ic_file_download_black_24dp,
                R.drawable.ic_assignment_turned_in_black_24dp,
        };

        Menu a = new Menu(covers[0], "Download Scheds", tchnum);
        menuList.add(a);

        a = new Menu(covers[1], "Check Attendance", tchnum);
        menuList.add(a);

        adapter.notifyDataSetChanged();
    }
}
