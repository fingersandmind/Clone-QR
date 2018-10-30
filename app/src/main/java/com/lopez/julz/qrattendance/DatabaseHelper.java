package com.lopez.julz.qrattendance;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlopez on 10/23/2018.
 */

public class DatabaseHelper {
    public SQLiteDatabase db;

    public DatabaseHelper(Context mContext) {
        db = mContext.openOrCreateDatabase("mdc", mContext.MODE_PRIVATE, null);
        createTables();
    }

    public void createTables() {
        try {
            String sems = "CREATE TABLE IF NOT EXISTS sems (" +
                    "id INTEGER PRIMARY KEY, " +
                    "sem VARCHAR(150))";

            String classes = "CREATE TABLE IF NOT EXISTS classes (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "tcid VARCHAR (15)," +
                    "course VARCHAR(50)," +
                    "time_start VARCHAR(20)," +
                    "time_end VARCHAR (20)," +
                    "day VARCHAR (10)," +
                    "room VARCHAR (50)," +
                    "semid INTEGER," +
                    "FOREIGN KEY (semid) REFERENCES sems(id)" +
                    ")";

            db.execSQL(sems);
            db.execSQL(classes);
        } catch ( Exception e ){
            Log.e("ERR_CRT_TBL", e.getMessage());
        }
    }

    public void insertIntoSems(String semid, String sem) {
        try {
            String sql = "";
            getSems();
            if (isSemExisting(semid)) {
                Log.e("OUT", "Sem ID exists");
            } else {
                Log.e("OUT", "Sem ID doesn't exist");
                sql = "INSERT INTO sems VALUES ('" + semid + "', '" + sem + "')";
                db.execSQL(sql);
            }
        } catch (Exception e) {
            Log.e("ERR_INSRT_SEM", e.getMessage());
        }
    }

    public boolean isSemExisting(String semid) {
        try {
            String sql = "SELECT * FROM sems WHERE id = '" + semid + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e("ERR_SEM_EXSTNG", e.getMessage());
            return false;
        }
    }

    public List<Semester> getSems() {
        try {
            List<Semester> semesters = new ArrayList<>();
            String sql = "SELECT * FROM sems ORDER BY id DESC";
            Cursor cursor = db.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                semesters.add(new Semester(cursor.getString(1), cursor.getString(0)));
                Log.e("OUT", cursor.getString(1));
            }
            return semesters;
        } catch (Exception e) {
            Log.e("ERR_GETTING_SEM", e.getMessage());
            return null;
        }
    }

    public Semester getSem(String sem) {
        try {
            String sql = "SELECT * FROM sems WHERE sem = '" + sem + "'";
            Cursor cursor = db.rawQuery(sql, null);
            cursor.moveToNext();
            return new Semester(cursor.getString(1), cursor.getString(0));
        } catch (Exception e) {
            Log.e("ERR_GET_SEM", e.getMessage());
            return null;
        }
    }

    public boolean insertToClasses(String tcid, String course, String tStart, String tEnd, String day, String room, String semid) {
        try {
            String sql = "";
            sql = "INSERT INTO classes VALUES (NULL, '" + tcid + "', '"+ course +"'," +
                    "'" + tStart + "'," +
                    "'" + tEnd + "'," +
                    "'" + day + "'," +
                    "'" + room + "', " +
                    "'" + semid + "')";
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            Log.e("ERR_INSRT_CLASS", e.getMessage());
            return false;
        }
    }

    public boolean isClassessExisting(String semId) {
        try {
            String sql = "SELECT * FROM classes WHERE semid = '" + semId + "'";
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            Log.e("ERR_CLASS_EXSTNG", e.getMessage());
            return false;
        }
    }

    public List<Classes> getClasses(String semid) {
        try {
            List<Classes> classes = new ArrayList<>();

            String sql = "SELECT * FROM classes WHERE semid = '" + semid + "'";
            Cursor cursor = db.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                classes.add(new Classes(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)));
            }
            return classes;
        }catch (Exception e) {
            Log.e("ERR_GET_CLASSES", e.getMessage());
            return null;
        }
    }

    public void deleteFromClasses(String semid) {
        try {
            String sql = "DELETE FROM classes WHERE semid = '"+ semid +"'";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteClasses() {
        try {
            String sql = "DELETE FROM classes";
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
