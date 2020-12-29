package com.shelter.review;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.shelter.review.fragment.MyFragment1;
import com.shelter.review.fragment.MyFragment2;

public class MainActivity extends AppCompatActivity implements Handler.Callback, View.OnClickListener {
    private FragmentManager fragmentManager;
    private MyFragment1 myFragment1 = new MyFragment1();
    private MyFragment2 myFragment2 = new MyFragment2();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Shelter", "MainActivity onCreate()");
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, myFragment1).commit();

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (v.getId()) {
            case R.id.btn1:
                transaction.replace(R.id.fragment_container, myFragment1).commit();
                break;
            case R.id.btn2:
                transaction.replace(R.id.fragment_container, myFragment2);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Shelter", "MainActivity onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Shelter", "MainActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Shelter", "MainActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Shelter", "MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Shelter", "MainActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Shelter", "MainActivity onDestroy()");
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("Shelter", "MainActivity onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("Shelter", "MainActivity onRestoreInstanceState()");
    }

    public void startSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        Log.i("Shelter", "MainActivity handleMessage()");
        return true;
    }

}
