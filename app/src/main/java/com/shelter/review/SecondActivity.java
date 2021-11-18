package com.shelter.review;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.shelter.review.anotation.BindView;
import com.shelter.review.anotation.InjectUtil;

/**
 * @author: Shelter
 * Create time: 2020/12/18, 0:20.
 */
public class SecondActivity extends Activity {
  @BindView(R.id.iv)
  private ImageView iv;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    InjectUtil.inject(this);
    iv.setImageResource(R.drawable.lock_bg);
    Log.i("Shelter", "SecondActivity onCreate()");
    try {
      Class.forName("");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
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
