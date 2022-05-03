package com.shelter.commonlib.utils

import android.content.Context
import android.content.res.Resources

/**
 * @author: Shelter
 * Create time: 2022/4/30, 21:16.
 */
object DensityUtil {
    fun dp2px(context: Context, dp: Int) : Int {
        val scale = context.resources.displayMetrics.density
        return (dp * scale + 0.5F).toInt()
    }

    fun dp2px(dp: Int) : Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (dp * scale + 0.5F).toInt()
    }


    fun px2dp(context: Context, px: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (px / scale + 0.5F).toInt()
    }
}