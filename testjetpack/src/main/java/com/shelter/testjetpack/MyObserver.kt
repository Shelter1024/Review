package com.shelter.testjetpack

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * @author: Shelter
 * Create time: 2022/8/16, 11:31.
 */
class MyObserver : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        Log.d("Shelter", "MyObserver onCreate: ")
    }

    override fun onResume(owner: LifecycleOwner) {
        Log.d("Shelter", "MyObserver onResume: ")
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d("Shelter", "MyObserver onPause: ")
    }

}