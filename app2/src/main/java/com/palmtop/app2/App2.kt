package com.palmtop.app2

import android.app.Application
import android.app.Application.ActivityLifecycleCallbacks
import android.app.Activity
import android.os.Bundle
import android.util.Log
import kotlin.properties.Delegates

/**
 * @author: Shelter
 * Create time: 2021/8/11, 10:04.
 */
class App2 : Application() {
    companion object {
        var instance : App2 by Delegates.notNull()
    }

//    companion object {
//        var instance : App2? = null
//        fun instance() = instance!!
//    }

    var lifecycleCallbacks: ActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.d("Shelter", "OtherApplication onActivityCreated() $activity")
        }

        override fun onActivityStarted(activity: Activity) {
            Log.d("Shelter", "OtherApplication onActivityStarted() $activity")
        }

        override fun onActivityResumed(activity: Activity) {
            Log.d("Shelter", "OtherApplication onActivityResumed() $activity")
        }

        override fun onActivityPaused(activity: Activity) {
            Log.d("Shelter", "OtherApplication onActivityPaused() $activity")
        }

        override fun onActivityStopped(activity: Activity) {
            Log.d("Shelter", "OtherApplication onActivityStopped() $activity")
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {
            Log.d("Shelter", "OtherApplication onActivityDestroyed() $activity")
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(lifecycleCallbacks)
    }
}