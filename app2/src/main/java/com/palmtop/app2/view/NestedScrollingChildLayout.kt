package com.palmtop.app2.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat
import kotlin.math.max

/**
 * @author: Shelter
 * Create time: 2022/5/6, 16:24.
 */
class NestedScrollingChildLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr),
    NestedScrollingChild {
    private val childedHelper = NestedScrollingChildHelper(this)
    private var lastY = 0f
    private var contentHeight = 0

    init {
        childedHelper.isNestedScrollingEnabled = true
    }

    //必须要重写onMeasure方法，否则子view（TextView）的高度最多就是父亲的高度
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        contentHeight = 0
        val height = MeasureSpec.getSize(heightMeasureSpec)
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED)
            measureChild(child, widthMeasureSpec, newHeightMeasureSpec)
            Log.d("Shelter", "NestedScrollingChild onMeasure: child:$child")
            if (child != null) {
                contentHeight += child.measuredHeight
            }
        }
        Log.d("Shelter", "NestedScrollingChild onMeasure: contentHeight:$contentHeight")
        setMeasuredDimension(
            MeasureSpec.getSize(widthMeasureSpec),
            MeasureSpec.getSize(heightMeasureSpec)
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("Shelter", "NestedScrollingChild onTouchEvent: action: ${event?.action}")
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastY = event.rawY + 0.5f
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = lastY - event.rawY + 0.5f
                val consumed = IntArray(2)
                if (childedHelper.dispatchNestedPreScroll(0, dy.toInt(), consumed, null)) {
                    Log.d("Shelter", "NestedScrollingChild onTouchEvent: dispatchNestedPreScroll")
                } else {
                    Log.d(
                        "Shelter",
                        "NestedScrollingChild onTouchEvent: scrollBy $dy scrollY:$scrollY"
                    )
                    scrollBy(0, dy.toInt())
                }
//                val unConsumedY = dy - consumed[1]
//                childedHelper.dispatchNestedScroll(0, consumed[1], 0, unConsumedY.toInt(), null)
                lastY = event.rawY
            }
            MotionEvent.ACTION_CANCEL -> stopNestedScroll()
            MotionEvent.ACTION_UP -> stopNestedScroll()
        }
        return true
    }

    override fun scrollTo(x: Int, y: Int) {
        Log.d(
            "Shelter",
            "NestedScrollingChild scrollTo: y = $y contentHeight = $contentHeight measuredHeight = $measuredHeight"
        )
        var newY: Int = y
        if (y < 0) {
            newY = 0
        }
        if (y > contentHeight) {
            newY = contentHeight
        }
        Log.d("Shelter", "NestedScrollingChild scrollTo: newY :$newY, scrollY:$scrollY")
        if (newY != scrollY) {
            super.scrollTo(x, newY)
        }
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.d("Shelter", "NestedScrollingChildLayout setNestedScrollingEnabled: enabled:$enabled")
        childedHelper.isNestedScrollingEnabled = true
    }

    override fun isNestedScrollingEnabled(): Boolean {
        return childedHelper.isNestedScrollingEnabled
    }

    override fun startNestedScroll(axes: Int): Boolean {
        return childedHelper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        childedHelper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent(): Boolean {
        return childedHelper.hasNestedScrollingParent()
    }

    override fun dispatchNestedScroll(
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        offsetInWindow: IntArray?
    ): Boolean {
        return childedHelper.dispatchNestedScroll(
            dxConsumed,
            dyConsumed,
            dxUnconsumed,
            dyUnconsumed,
            offsetInWindow
        )
    }

    override fun dispatchNestedPreScroll(
        dx: Int,
        dy: Int,
        consumed: IntArray?,
        offsetInWindow: IntArray?
    ): Boolean {
        return childedHelper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedFling(
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return childedHelper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return childedHelper.dispatchNestedPreFling(velocityX, velocityY)
    }
}