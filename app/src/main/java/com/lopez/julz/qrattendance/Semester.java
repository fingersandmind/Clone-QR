package com.lopez.julz.qrattendance;

/**
 * Created by jlopez on 10/22/2018.
 */

public class Semester {

    private String sem, semid, tchnum;

    public Semester(String sem, String semid) {
        this.sem = sem;
        this.semid = semid;
    }

    public Semester(String sem, String semid, String tchnum) {
        this.sem = sem;
        this.semid = semid;
        this.tchnum = tchnum;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSemid() {
        return semid;
    }

    public void setSemid(String semid) {
        this.semid = semid;
    }

    public String getTchnum() {
        return tchnum;
    }

    public void setTchnum(String tchnum) {
        this.tchnum = tchnum;
    }
}
