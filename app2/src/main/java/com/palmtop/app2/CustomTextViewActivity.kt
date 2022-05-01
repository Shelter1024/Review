package com.palmtop.app2

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_custom_text_view.*

class CustomTextViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_text_view)
        initView()
    }

    private fun initView() {
        //setPercent
        Log.d("Shelter", "CustomTextViewActivity initView: customTextView:$customTextView")
        val animator = ObjectAnimator.ofFloat(customTextView, "percent", 0F, 1F)
        animator.addUpdateListener {
//            Log.d("Shelter", "CustomTextViewActivity initView: it.animatedValue: ${it.animatedValue}")
        }
        animator.setDuration(5000)
            .start()
    }
}