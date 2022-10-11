package com.palmtop.app2

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CloseViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close_view)
        val closeView: CloseView = findViewById(R.id.close_view)
        closeView.startTimer(3)

        closeView.setOnClickListener {
            Toast.makeText(this, "点击了", Toast.LENGTH_LONG).show()
        }
    }

}