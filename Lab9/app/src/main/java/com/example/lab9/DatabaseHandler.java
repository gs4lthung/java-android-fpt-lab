package com.example.lab9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static DatabaseHandler instance;

    public static synchronized DatabaseHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHandler(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHandler(Context context) {
        super(context, "jobs.sqlite", null, 2);
    }

    public Cursor getJobs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM jobs", null);
    }

    public void addJob(String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO jobs (job_title, description) VALUES (?, ?)", new Object[]{title, description});
    }

    public void updateJob(int id, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE jobs SET job_title = ?, description = ? WHERE id = ?", new Object[]{title, description, id});
    }

    public void deleteJob(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM jobs WHERE id = ?", new Object[]{id});
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_jobs_table = "CREATE TABLE jobs (id INTEGER PRIMARY KEY AUTOINCREMENT, job_title TEXT, description TEXT)";
        db.execSQL(create_jobs_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "jobs");
        onCreate(db); // recreate the database
    }
}
