package com.lopez.julz.qrattendance;

import android.widget.ImageView;

/**
 * Created by jlopez on 10/22/2018.
 */

public class Menu {

    private int icon;
    private String title, tchnum;

    public Menu(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }

    public Menu(int icon, String title, String tchnum) {
        this.icon = icon;
        this.title = title;
        this.tchnum = tchnum;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTchnum() {
        return tchnum;
    }

    public void setTchnum(String tchnum) {
        this.tchnum = tchnum;
    }
}
