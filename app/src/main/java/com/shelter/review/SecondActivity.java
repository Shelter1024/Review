package com.shelter.review;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;

/**
 * @author: Shelter
 * Create time: 2020/12/18, 0:20.
 */
public class SecondActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.i("Shelter", "SecondActivity onCreate()");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.i("Shelter", "SecondActivity onRestart()");
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.i("Shelter", "SecondActivity onStart()");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.i("Shelter", "SecondActivity onResume()");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.i("Shelter", "SecondActivity onPause()");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.i("Shelter", "SecondActivity onStop()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.i("Shelter", "MainActivity onDestroy()");
  }

}
