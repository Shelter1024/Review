package com.shelter.review.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import org.jetbrains.anko.switch
import kotlin.math.abs
import kotlin.math.log

/**
 * @author: Shelter
 * Create time: 2022/4/30, 13:46.
 */
class CustomViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleArray: Int = 0
) : ViewPager(context, attrs) {
    private var lastX: Float = 0F
    private var lastY: Float = 0F

    init {

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        println("Shelter CustomViewPager dispatchTouchEvent action: ${ev?.action}")
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = ev.x
                lastY = ev.y
//                ViewCompat.setNestedScrollingEnabled(this, true)
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = ev.x - lastX
                val deltaY = ev.y - lastY
                println("Shelter lastX: $lastX  lastY: $lastY ev.x: ${ev.x}  ev.y: ${ev.y} deltaX: $deltaX  deltaY: $deltaY")
                if (abs(deltaY) > abs(deltaX)) {
                    parent.requestDisallowInterceptTouchEvent(false)
                    return false
                }
            }
        }
        val dispatchTouchEvent = super.dispatchTouchEvent(ev)
        println("Shelter CustomViewPager dispatchTouchEvent: $dispatchTouchEvent")
        return dispatchTouchEvent
    }

}