package com.example.hostapp;

import static android.view.Window.ID_ANDROID_CONTENT;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //运行时权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

    public void callPluginMethod(View view) {
//        try {
//            Class testClass = Class.forName("com.example.plugin.Test");
//            Method printMethod = testClass.getMethod("print");
//            printMethod.invoke(null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.plugin", "com.example.plugin.PluginActivity"));
        startActivity(intent);
        View contentParent = findViewById(ID_ANDROID_CONTENT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("Shelter", "MainActivity onRequestPermissionsResult");

    }
}