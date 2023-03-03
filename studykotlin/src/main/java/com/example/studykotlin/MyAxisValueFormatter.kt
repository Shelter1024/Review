package com.example.studykotlin

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

/**
 * @author: Shelter
 * Create time: 2023/2/24, 15:46.
 */
class MyAxisValueFormatter() : IAxisValueFormatter {

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        Log.d("Shelter", "MyAxisValueFormatter getFormattedValue $value axis:${axis.toString()}")

        val text = when (value.toInt()) {
            0 -> "周一"
            1 -> "周二"
            2 -> "周三"
            3 -> "周四"
            4 -> "周五"
            5 -> "周六"
            6 -> "周日"
            else -> "以后"
        }
        return text
    }
}