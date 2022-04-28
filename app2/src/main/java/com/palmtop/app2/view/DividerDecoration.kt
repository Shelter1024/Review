package com.palmtop.app2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.palmtop.app2.R


/**
 * @author: Shelter
 * Create time: 2022/4/5, 16:52.
 */
class SimpleDividerDecoration(val context: Context?) : ItemDecoration() {
    private val dividerHeight: Int
    private val dividerPaint: Paint

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent!!, state!!)
        outRect.bottom = dividerHeight
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        for (i in 0 until childCount - 1) {
            val view: View = parent.getChildAt(i)
            val top: Float = view.getBottom().toFloat()
            val bottom: Float = (view.getBottom() + dividerHeight).toFloat()
            c.drawRect(left.toFloat(), top, right.toFloat(), bottom, dividerPaint)
        }
    }

    init {
        dividerPaint = Paint()
        context?.getResources()?.let { dividerPaint.setColor(it.getColor(R.color.purple_200)) }
        dividerHeight = context?.getResources()?.getDimensionPixelSize(R.dimen.divider_height) ?: 30
    }
}