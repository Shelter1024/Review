package com.palmtop.app2.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import com.shelter.commonlib.utils.DensityUtil

/**
 * @author: Shelter
 * Create time: 2022/5/6, 16:22.
 */
class NestedScrollingParentLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
    ): LinearLayout(context, attributeSet, defStyleAttr),
    NestedScrollingParent {

    private val parentHelper = NestedScrollingParentHelper(this)
    private val topViewHeight = DensityUtil.dp2px(100)
    private var realHeight: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = MeasureSpec.getSize(heightMeasureSpec)
        realHeight = 0
        var newHeightMeasureSpec = 0
//        Log.d("Shelter", "NestedScrollingParentLayout onMeasure: childCount = $childCount height = ${MeasureSpec.getSize(heightMeasureSpec)}")
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.UNSPECIFIED)
            measureChild(child, widthMeasureSpec, newHeightMeasureSpec)
            realHeight += child.measuredHeight
//            Log.d("Shelter", "NestedScrollingParentLayout onMeasure  i: $i  height:${child.measuredHeight} ")
        }
//        Log.d("Shelter", "NestedScrollingParentLayout onMeasure: realHeight:$realHeight")
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(newHeightMeasureSpec))
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        Log.d("Shelter", "NestedScrollingParentLayout setNestedScrollingEnabled: enabled:$enabled")
        super.setNestedScrollingEnabled(enabled)
    }


    override fun onStartNestedScroll(child: View, target: View, axes: Int): Boolean {
        return axes and ViewCompat.SCROLL_AXIS_VERTICAL != 0
    }

    override fun onNestedScrollAccepted(child: View, target: View, axes: Int) {
        Log.d("Shelter", "NestedScrollingParentLayout onNestedScrollAccepted: ")
        parentHelper.onNestedScrollAccepted(child, target, axes)
    }

    override fun onStopNestedScroll(target: View) {
        Log.d("Shelter", "NestedScrollingParentLayout onStopNestedScroll: ")
        parentHelper.onStopNestedScroll(target)
    }

    override fun onNestedScroll(
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int
    ) {
        Log.d("Shelter", "NestedScrollingParentLayout onNestedScroll: ")
    }

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        Log.d("Shelter", "NestedScrollingParentLayout onNestedPreScroll: dy:$dy, scrollY:$scrollY, topViewHeight:$topViewHeight")
        if (canScroll(dy)) {
            scrollBy(0, dy)
            consumed[1] = dy
        }
    }

    fun canScroll(dy: Int): Boolean {
        if (dy < 0 && scrollY > 0) {
            return true
        }
        if (dy > 0 && scrollY < topViewHeight) {
            return true
        }
        return false
    }

    override fun scrollTo(x: Int, y: Int) {
        Log.d("Shelter", "NestedScrollingParentLayout scrollTo: y:$y topViewHeight:$topViewHeight")
        var newY = y
        if (y < 0) {
            newY = 0
        }
        if (y > topViewHeight) {
            newY = topViewHeight
        }
        super.scrollTo(x, newY)
    }

    override fun onNestedFling(
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        return true
    }

    override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
        return true
    }

    override fun getNestedScrollAxes(): Int {
        return parentHelper.nestedScrollAxes
    }
}