package com.shelter.review;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements Handler.Callback{
    private Handler handler;

    private ContentObserver contentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler(this);
        contentObserver = new ContentObserver(handler) {
            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                Log.i("Shelter", "MainActivity onChange() 1");
            }

            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                Log.i("Shelter", "MainActivity onChange() 2");
            }
        };
        Log.i("Shelter", "MainActivity onCreate()");
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://com.example.shelter/user");
        contentResolver.registerContentObserver(uri, false, contentObserver);
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", "3");
        contentValues.put("name", "Kobe Bryant");
        contentResolver.insert(uri, contentValues);

        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.i("Shelter", "_id = " + cursor.getInt(0) + ", name = " + cursor.getString(1));
        }
        cursor.close();

        startService(new Intent(this, MainService.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Shelter", "MainActivity onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Shelter", "MainActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Shelter", "MainActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Shelter", "MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Shelter", "MainActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Shelter", "MainActivity onDestroy()");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Shelter", "MainActivity onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Shelter", "MainActivity onRestoreInstanceState()");
    }

    public void startSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        Log.i("Shelter", "MainActivity handleMessage()");
        return true;
    }
}
