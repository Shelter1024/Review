package com.shelter.review.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * @author: Shelter
 * Create time: 2020/12/29, 18:16.
 */
public class MyOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shelter.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_USER = "user";
    public static final String TABLE_JOB = "job";

    public MyOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_JOB + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, job TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}