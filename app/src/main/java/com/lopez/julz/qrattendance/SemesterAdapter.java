package com.lopez.julz.qrattendance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jlopez on 10/22/2018.
 */

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.ViewHolder> {

    public Context mContext;
    public List<Semester> semesterList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView sem, semid;
        public LinearLayout sems;

        public ViewHolder(View itemView) {
            super(itemView);
            sem = (TextView) itemView.findViewById(R.id.semester);
            semid= (TextView) itemView.findViewById(R.id.sem_id);
            sems = (LinearLayout) itemView.findViewById(R.id.sems);
        }
    }

    public SemesterAdapter(Context mContext, List<Semester> semesterList) {
        this.mContext = mContext;
        this.semesterList = semesterList;
    }

    @Override
    public SemesterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sem_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SemesterAdapter.ViewHolder holder, int position) {
        Semester semester = semesterList.get(position);
        holder.sem.setText(semester.getSem());
        holder.semid.setText(semester.getSemid());

        holder.sems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ChooseSemester.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return semesterList.size();
    }
}
