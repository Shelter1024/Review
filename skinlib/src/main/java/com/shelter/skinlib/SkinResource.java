package com.shelter.skinlib;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @author: Shelter
 * Create time: 2022/4/28, 21:56.
 */
public class SkinResource {
    private Resources appResource;
    private Resources skinResource;
    private String skinPkg;

    public SkinResource(Resources resources, Resources appResources, String skinPkg) {
        this.skinResource = resources;
        this.appResource = appResources;
        this.skinPkg = skinPkg;
    }

    public int getResourceIdentifier(int resId) {
        String resourceName = appResource.getResourceName(resId);
        String resourceEntryName = appResource.getResourceEntryName(resId);
        String resourceTypeName = appResource.getResourceTypeName(resId);
        Log.d("Shelter", "SkinResource getResourceIdentifier() resourceName = " + resourceName + ", resourceEntryName = " + resourceEntryName + ", resourceTypeName = " + resourceTypeName);
        return skinResource.getIdentifier(resourceEntryName, resourceTypeName, skinPkg);
    }


    public int getColor(int resId) {
        int skinId = getResourceIdentifier(resId);
        Log.d("Shelter", "SkinResource getColor() resId = " + resId + ", skinId = " + skinId);
        if (skinId == 0) {
            return appResource.getColor(resId);
        }
        return skinResource.getColor(skinId);
    }

    public Drawable getDrawable(int resId) {
        int skinId = getResourceIdentifier(resId);
        Log.d("Shelter", "SkinResource getDrawable() skinId = " + skinId + ", resId = " + resId);
        if (skinId == 0) {
            return appResource.getDrawable(resId);
        }
        return skinResource.getDrawable(skinId);
    }

    public ColorStateList getColorStateList(int resId) {
        int skinId = getResourceIdentifier(resId);
        if (skinId == 0) {
            return appResource.getColorStateList(resId);
        }
        return skinResource.getColorStateList(skinId);
    }

    public Object getBackGround(int resId) {
        String resourceTypeName = appResource.getResourceTypeName(resId);
        if ("color".equals(resourceTypeName)) {
            return getColor(resId);
        } else {
            return getDrawable(resId);
        }
    }

}