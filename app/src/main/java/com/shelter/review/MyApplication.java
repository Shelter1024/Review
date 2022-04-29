package com.shelter.review;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.shelter.skinlib.SkinManager;

/**
 * @author: Shelter
 * Create time: 2021/8/11, 10:01.
 */
public class MyApplication extends MultiDexApplication {
    ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            Log.d("Shelter", "MyApplication onActivityCreated() " + activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            Log.d("Shelter", "MyApplication onActivityStarted() " + activity);
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            Log.d("Shelter", "MyApplication onActivityResumed() " + activity);
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            Log.d("Shelter", "MyApplication onActivityPaused() " + activity);
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            Log.d("Shelter", "MyApplication onActivityStopped() " + activity);
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            Log.d("Shelter", "MyApplication onActivityDestroyed() " + activity);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
//        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }
}