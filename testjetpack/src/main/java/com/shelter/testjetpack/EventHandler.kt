package com.shelter.testjetpack

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * @author: Shelter
 * Create time: 2022/8/17, 16:34.
 */
class EventHandler(val context: Context) {

    fun onBtnClick(view: View) {
        Toast.makeText(context, "我被点击了", Toast.LENGTH_LONG).show()
    }
}