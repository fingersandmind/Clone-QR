package com.lopez.julz.qrattendance;

/**
 * Created by jlopez on 10/22/2018.
 */

public class Semester {

    private String sem, semid;

    public Semester(String sem, String semid) {
        this.sem = sem;
        this.semid = semid;
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
}
