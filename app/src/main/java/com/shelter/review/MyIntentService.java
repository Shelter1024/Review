package com.shelter.review;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author: Shelter
 * Create time: 2021/1/4, 16:33.
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("Shelter", "MyIntentService onCreate()");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.i("Shelter", "MyIntentService onStartCommand() startId = " + startId + ", action = " + intent.getAction());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("Shelter", "MyIntentService onHandleIntent() action = " + intent.getAction());
    }


    /**
     * 所有任务执行完，会自动销毁；但是每次执行onHandlerIntent后都会执行一次stopSelf()，猜测是根据startId来判断
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Shelter", "MyIntentService onDestroy()");
    }
}