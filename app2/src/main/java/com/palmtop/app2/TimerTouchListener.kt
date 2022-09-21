package com.palmtop.app2

import android.util.Log
import android.view.MotionEvent
import android.view.View
import org.jetbrains.anko.windowManager

/**
 * @author: Shelter
 * Create time: 2022/9/14, 16:04.
 */
class TimerTouchListener: View.OnTouchListener {
    var downX: Float = 0f
    var downY: Float = 0f
    var rawX: Float = 0f
    var rawY: Float = 0f


    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        Log.d("Shelter", "TimerTouchListener onTouch")
        if (event == null || view == null) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rawX = event.rawX
                rawY = event.rawY
                downX = event.rawX
                downY = event.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val nowX = event.rawX
                val nowY = event.rawY
                val movedX = nowX - downX
                val movedY = nowY - downY
                downX = nowX
                downY = nowY
                view.layoutParams.apply {
                    view.x += movedX
                    view.y += movedY
                }
                //更新悬浮球控件位置
                view.context?.windowManager?.updateViewLayout(view, view.layoutParams)
            }
            else -> {
//                view.layoutParams.apply {
//                    view.x = rawX
//                    view.y = rawY
//                }
//                //更新悬浮球控件位置
//                view.context?.windowManager?.updateViewLayout(view, view.layoutParams)
            }
        }
        return false
    }
}