package com.shelter.review;

import java.io.File;

/**
 * @author: Shelter
 * Create time: 2021/8/26, 15:19.
 */
public class Utils {

    public static String getClassFilePath(Class clazz) {
        // file:/Users/zhy/hongyang/repo/BlogDemo/app/build/intermediates/javac/debug/classes/
        // buildDir: /E:/project/Review/app/build/intermediates/javac/debug/classes/, name: com.shelter.review
        String buildDir = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        String fileName = clazz.getSimpleName() + ".class";
        String name = clazz.getPackage().getName();
        System.out.println("buildDir: " + buildDir + ", name: " + name);
        File file = new File(buildDir + name.replaceAll("[.]", "/") + "/", fileName);
        return file.getAbsolutePath();
    }
} 