package com.shelter.skinlib;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * @author: Shelter
 * Create time: 2022/4/28, 21:44.
 */
public class SkinManager extends Observable {

    private static volatile SkinManager INSTANCE;
    private Application application;
    private SkinResource skinResource;
    private Resources appResources;

    public static SkinManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SkinManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SkinManager();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Application application) {
        this.application = application;
        appResources = application.getResources();
        SkinActivityLifecycleCallback lifecycleCallback = new SkinActivityLifecycleCallback(this);
        application.registerActivityLifecycleCallbacks(lifecycleCallback);
    }

    public void loadSkin(String skinPath) {
        if (TextUtils.isEmpty(skinPath)) {
            Log.d("Shelter", "SkinManager loadSkin() skinPath 路径不存在");
            return;
        }
        try {
            Class<AssetManager> assetClazz = AssetManager.class;
            AssetManager assetManager = assetClazz.newInstance();
            Method addAssetPath = assetClazz.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPath);

            Resources resources = new Resources(assetManager, appResources.getDisplayMetrics(), appResources.getConfiguration());
            PackageManager packageManager = application.getPackageManager();
            PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
            String pkgName = packageArchiveInfo.packageName;
            //SkinManager loadSkin() pkgName = com.shelter.skinapp
            Log.d("Shelter", "SkinManager loadSkin() pkgName = " + pkgName);
            skinResource = new SkinResource(resources, appResources, pkgName);
        } catch (Exception e) {
            Log.d("Shelter", "SkinManager loadSkin() error: " + e.getMessage());
            e.printStackTrace();
        }

        //通知观察者
        setChanged();
        notifyObservers();
    }


    public int getColor(int resId) {
        if (skinResource == null) {
            return appResources.getColor(resId);
        }
        return skinResource.getColor(resId);
    }

    public Drawable getDrawable(int resId) {
        if (skinResource == null) {
            return appResources.getDrawable(resId);
        }
        return skinResource.getDrawable(resId);
    }

    public ColorStateList getColorStateList(int resId) {
        if (skinResource == null) {
            return appResources.getColorStateList(resId);
        }
        return skinResource.getColorStateList(resId);
    }

    public Object getBackground(int resId) {
        if (skinResource == null) {
            String resourceTypeName = appResources.getResourceTypeName(resId);
            if ("color".equals(resourceTypeName)) {
                return getColor(resId);
            } else {
                return getDrawable(resId);
            }
        }
        return skinResource.getBackGround(resId);
    }

}