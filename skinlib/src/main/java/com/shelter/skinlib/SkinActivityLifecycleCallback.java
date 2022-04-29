package com.shelter.skinlib;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.LayoutInflaterCompat;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * @author: Shelter
 * Create time: 2022/4/28, 21:48.
 */
public class SkinActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private Observable observable;
    private final Map<Activity, SkinLayoutInflaterFactory> layoutFactories = new HashMap<>();

    public SkinActivityLifecycleCallback(Observable observable) {
        this.observable = observable;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        Log.d("Shelter", activity + " onActivityCreated()");
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        //Android Q 之前有效，Android Q及之后报错: java.lang.NoSuchFieldException: No field mFactorySet in class Landroid/view/LayoutInflater; (declaration of 'android.view.LayoutInflater' appears in /system/framework/framework.jar!classes3.dex)
//        try {
//            Field factorySetField = LayoutInflater.class.getDeclaredField("mFactorySet");
//            factorySetField.setAccessible(true);
//            factorySetField.setBoolean(layoutInflater, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d("Shelter", "SkinActivityLifecycleCallback onActivityCreated() error: " + e.getMessage());
//        }

        SkinLayoutInflaterFactory skinLayoutInflaterFactory = new SkinLayoutInflaterFactory(activity);
        forceSetFactory2(skinLayoutInflaterFactory, layoutInflater);
        layoutFactories.put(activity, skinLayoutInflaterFactory);

        observable.addObserver(skinLayoutInflaterFactory);
    }

    private static void forceSetFactory2(SkinLayoutInflaterFactory skinLayoutInflaterFactory, LayoutInflater inflater) {
//        Class<LayoutInflaterCompat> compatClass = LayoutInflaterCompat.class;
        Class<LayoutInflater> inflaterClass = LayoutInflater.class;
        try {
//            Field sCheckedField = compatClass.getDeclaredField("sCheckedField");
//            sCheckedField.setAccessible(true);
//            sCheckedField.setBoolean(inflater, false);
            Field mFactory = inflaterClass.getDeclaredField("mFactory");
            mFactory.setAccessible(true);
            Field mFactory2 = inflaterClass.getDeclaredField("mFactory2");
            mFactory2.setAccessible(true);
//            if (inflater.getFactory2() != null) {
//                skinLayoutInflaterFactory.setInterceptFactory2(inflater.getFactory2());
//            } else if (inflater.getFactory() != null) {
//                skinLayoutInflaterFactory.setInterceptFactory(inflater.getFactory());
//            }

            mFactory2.set(inflater, skinLayoutInflaterFactory);
            mFactory.set(inflater, skinLayoutInflaterFactory);
        } catch (Exception e) {
            Log.d("Shelter", "SkinActivityLifecycleCallback forceSetFactory2() error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        SkinLayoutInflaterFactory layoutInflaterFactory = layoutFactories.remove(activity);
        SkinManager.getInstance().deleteObserver(layoutInflaterFactory);
    }

}