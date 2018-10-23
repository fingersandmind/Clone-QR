package com.lopez.julz.qrattendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by jlopez on 10/22/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    private List<Menu> menuList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView title;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.menu_card);
            icon = (ImageView) itemView.findViewById(R.id.menu_icon);
            title = (TextView) itemView.findViewById(R.id.menu_title);
        }
    }

    public MenuAdapter(Context mContext, List<Menu> menuList) {
        this.mContext = mContext;
        this.menuList = menuList;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_cards, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder, final int position) {
        final Menu menu = menuList.get(position);
        holder.icon.setImageResource(menu.getIcon());
        holder.title.setText(menu.getTitle());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("TCHNUM", menu.getTchnum());
                    Intent intent = new Intent(mContext, ChooseSemester.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                } else if (position == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("TCHNUM", menu.getTchnum());
                    Intent intent = new Intent(mContext, ChooseClass.class);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }
}
