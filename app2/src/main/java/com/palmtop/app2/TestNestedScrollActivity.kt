package com.palmtop.app2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test_nested_scroll.*

class TestNestedScrollActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_nested_scroll)
        scrollTextView.text = resources.getText(R.string.content)
    }
}