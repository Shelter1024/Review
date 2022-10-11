package com.example.testproguard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestProguardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_proguard)
        val test = Test()
        test.testProguard()
    }
}