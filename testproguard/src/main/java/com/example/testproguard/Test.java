package com.example.testproguard;

import android.util.Log;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: Shelter
 * Create time: 2022/9/23, 00:25.
 */
public class Test {

    public void testProguard() {
        Log.d("Shelter", "Test testProguard start");
        Student student = new Student();
        Class<? extends Student> aClass = student.getClass();
        Type genericSuperclass = aClass.getGenericSuperclass();
        Log.d("Shelter", "Test testProguard genericSuperclass = " + genericSuperclass);
        boolean isParameterizedType = genericSuperclass instanceof ParameterizedType;
        Log.d("Shelter", "Test testProguard isParameterizedType = " + isParameterizedType);
    }

} 