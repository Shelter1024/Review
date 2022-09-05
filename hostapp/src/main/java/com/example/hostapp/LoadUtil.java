package com.example.hostapp;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * @author: Shelter
 * Create time: 2022/9/1, 12:55.
 */
public class LoadUtil {
    private static final String apkPath = "/sdcard/plugin-debug.apk";

    public static void loadPlugin(Context context) {
        Log.d("Shelter", "LoadUtil loadPlugin start");
        BaseDexClassLoader pathClassLoader = (BaseDexClassLoader) context.getClassLoader();
        //需要申请存储权限
//        String dexPath = "/sdcard/A.dex";
        DexClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(), null, null);

        try {
            //反射获取dexPathList
            Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object dexPathList = pathListField.get(pathClassLoader);
            //反射获取宿主dexElements
            Class dexPathClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);
            Object[] dexElements = (Object[]) dexElementsField.get(dexPathList);
            //反射获取dex文件的dexElement
            Object pluginDexPathList = pathListField.get(dexClassLoader);
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginDexPathList);

            Class dexElemenClass = dexElements.getClass().getComponentType();
            Object[] newDexElements = (Object[]) Array.newInstance(dexElemenClass, dexElements.length + pluginDexElements.length);

            System.arraycopy(dexElements, 0, newDexElements, 0, dexElements.length);
            System.arraycopy(pluginDexElements, 0, newDexElements, dexElements.length, pluginDexElements.length);

            //使用反射设置回宿主的classloader
            dexElementsField.set(dexPathList, newDexElements);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("Shelter", "LoadUtil loadPlugin end");
    }

    public static void loadClass(Context context) {

        /**
         * 宿主dexElements = 宿主dexElements + 插件dexElements
         *
         * 1.获取宿主dexElements
         * 2.获取插件dexElements
         * 3.合并两个dexElements
         * 4.将新的dexElements 赋值到 宿主dexElements
         *
         * 目标：dexElements  -- DexPathList类的对象 -- BaseDexClassLoader的对象，类加载器
         *
         * 获取的是宿主的类加载器  --- 反射 dexElements  宿主
         *
         * 获取的是插件的类加载器  --- 反射 dexElements  插件
         */
        try {
            Class<?> clazz = Class.forName("dalvik.system.BaseDexClassLoader");
            Field pathListField = clazz.getDeclaredField("pathList");
            pathListField.setAccessible(true);

            Class<?> dexPathListClass = Class.forName("dalvik.system.DexPathList");
            Field dexElementsField = dexPathListClass.getDeclaredField("dexElements");
            dexElementsField.setAccessible(true);

            // 宿主的 类加载器
            ClassLoader pathClassLoader = context.getClassLoader();
            // DexPathList类的对象
            Object hostPathList = pathListField.get(pathClassLoader);
            // 宿主的 dexElements
            Object[] hostDexElements = (Object[]) dexElementsField.get(hostPathList);

            // 插件的 类加载器
            ClassLoader dexClassLoader = new DexClassLoader(apkPath, context.getCacheDir().getAbsolutePath(),
                    null, pathClassLoader);
            // DexPathList类的对象
            Object pluginPathList = pathListField.get(dexClassLoader);
            // 插件的 dexElements
            Object[] pluginDexElements = (Object[]) dexElementsField.get(pluginPathList);

            // 宿主dexElements = 宿主dexElements + 插件dexElements
//            Object[] obj = new Object[]; // 不行

            // 创建一个新数组
            Object[] newDexElements = (Object[]) Array.newInstance(hostDexElements.getClass().getComponentType(),
                    hostDexElements.length + pluginDexElements.length);

            System.arraycopy(hostDexElements, 0, newDexElements,
                    0, hostDexElements.length);
            System.arraycopy(pluginDexElements, 0, newDexElements,
                    hostDexElements.length, pluginDexElements.length);

            // 赋值
            // hostDexElements = newDexElements
            dexElementsField.set(hostPathList, newDexElements);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 