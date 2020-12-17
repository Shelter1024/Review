package com.shelter.review;

import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.i("Shelter", "MainActivity onCreate()");
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
}
