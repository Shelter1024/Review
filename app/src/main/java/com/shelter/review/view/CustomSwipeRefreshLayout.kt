package com.shelter.review.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.lang.reflect.Field


/**
 * @author: Shelter
 * Create time: 2022/4/30, 13:48.
 */
class CustomSwipeRefreshLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : SwipeRefreshLayout(context, attributeSet) {
    var startX: Float = 0F
    var startY: Float = 0F

    init {

    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        //1. 外部拦截法
//        when(ev?.action) {
//            MotionEvent.ACTION_DOWN -> {
//                startX = ev.x
//                startY = ev.y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val deltaX = abs(ev.x - startX)
//                val deltaY = abs(ev.y - startY)
//                if (deltaX > deltaY) {
//                    return false
//                }
//            }
//        }
        //2. 内部拦截法
        println("Shelter CustomSwipeRefreshLayout onInterceptTouchEvent action:${ev?.action}")
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            super.onInterceptTouchEvent(ev)
            println("Shelter CustomSwipeRefreshLayout onInterceptTouchEvent return false")
            return false
        }
        println("Shelter CustomSwipeRefreshLayout onInterceptTouchEvent return true")
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        println("Shelter CustomSwipeRefreshLayout dispatchTouchEvent: $dispatchTouchEvent")
        return dispatchTouchEvent
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val onTouchEvent = super.onTouchEvent(ev)
        println("Shelter CustomSwipeRefreshLayout onTouchEvent: $onTouchEvent")
        return onTouchEvent
    }

    override fun requestDisallowInterceptTouchEvent(b: Boolean) {
        //3. 处理滑动冲突的方法，反射！！！ 仅支持 API 29 之前版本
        val clazz: Class<*> = ViewGroup::class.java
        // FLAG_DISALLOW_INTERCEPT = 0x80000;
        //     1000 0000 0000 0000 0000       0x80000
        //10 1100 0100 0000  0101  0011     2900051
        //10 0010 0100 0100  0101  0011     2245715
        try {
            val mGroupFlagsField: Field = clazz.getDeclaredField("mGroupFlags")
            mGroupFlagsField.setAccessible(true)
            val c = mGroupFlagsField.get(this) as Int
            Log.e("Shelter", "dispatchTouchEvent: c $c")
            if (b) {
                //2900051&FLAG_DISALLOW_INTERCEPT =true
                mGroupFlagsField.set(this, 2900051)
            } else {
                //2245715&FLAG_DISALLOW_INTERCEPT =fasle
                mGroupFlagsField.set(this, 2245715)
            }
        } catch (e: Exception) {
            println("Shelter requestDisallowInterceptTouchEvent error: ${e.message}")
            e.printStackTrace()
        }
//        super.requestDisallowInterceptTouchEvent(b);
    }
}