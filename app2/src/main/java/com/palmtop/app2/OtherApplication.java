package com.palmtop.app2;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author: Shelter
 * Create time: 2021/8/11, 10:04.
 */
public class OtherApplication extends Application {
    ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            Log.d("Shelter", "OtherApplication onActivityCreated() " + activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {
            Log.d("Shelter", "OtherApplication onActivityStarted() " + activity);
        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {
            Log.d("Shelter", "OtherApplication onActivityResumed() " + activity);
        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {
            Log.d("Shelter", "OtherApplication onActivityPaused() " + activity);
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {
            Log.d("Shelter", "OtherApplication onActivityStopped() " + activity);
        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            Log.d("Shelter", "OtherApplication onActivityDestroyed() " + activity);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }
} 