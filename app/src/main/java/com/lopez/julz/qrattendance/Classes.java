package com.lopez.julz.qrattendance;

/**
 * Created by jlopez on 10/23/2018.
 */

public class Classes {

    private String course, timeStart, timeEnd, date, room;

    public Classes(String course, String timeStart, String timeEnd, String date, String room) {
        this.course = course;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.date = date;
        this.room = room;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
