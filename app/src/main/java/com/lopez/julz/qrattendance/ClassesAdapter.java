package com.lopez.julz.qrattendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jlopez on 10/23/2018.
 */

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ViewHolder> {

    public Context mContext;
    public List<Classes> classesList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView className, classDetails;
        public CardView classCard;

        public ViewHolder(View itemView) {
            super(itemView);
            className = (TextView) itemView.findViewById(R.id.class_name);
            classDetails = (TextView) itemView.findViewById(R.id.class_details);
            classCard = (CardView) itemView.findViewById(R.id.class_card);
        }
    }

    public ClassesAdapter(Context mContext, List<Classes> classesList) {
        this.mContext = mContext;
        this.classesList = classesList;
    }

    @Override
    public ClassesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.class_cards, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ClassesAdapter.ViewHolder holder, int position) {
        final Classes classes = classesList.get(position);
        holder.className.setText(classes.getCourse());
        holder.classDetails.setText(classes.getDate() + " " + classes.getTimeStart() + "-" + classes.getTimeEnd());

        holder.classCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("TCID", classes.getId());
                Intent intent = new Intent(mContext, QRScanAttendance.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return classesList.size();
    }
}
