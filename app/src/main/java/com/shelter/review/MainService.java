package com.shelter.review;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author: Shelter
 * Create time: 2020/12/29, 20:43.
 */
public class MainService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Shelter", "MainService onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Shelter", "MainService onStartCommand()");
        Uri uri = Uri.parse("content://com.example.shelter/user");
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        while(cursor.moveToNext()) {
            Log.i("Shelter", "MainService onStartCommand() _id = " + cursor.getInt(0) + ", name = " + cursor.getString(1));
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}