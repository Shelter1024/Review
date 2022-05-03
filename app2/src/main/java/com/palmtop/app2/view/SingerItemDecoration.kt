package com.palmtop.app2.view

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.palmtop.app2.SingerAdapter
import com.shelter.commonlib.utils.DensityUtil
import org.jetbrains.anko.padding

/**
 * @author: Shelter
 * Create time: 2022/5/2, 22:53.
 */
class SingerItemDecoration : RecyclerView.ItemDecoration() {
    private val groupHeadHeight:Float = DensityUtil.dp2px(70).toFloat()
    private val rectPaint = Paint()
    private val textPaint = Paint()
    private val textPaddingLeft: Float = DensityUtil.dp2px(5).toFloat()
    private val textRect = Rect()

    init {
        Log.d("Shelter", "SingerItemDecoration init: ")
        initPaint()
    }

    private fun initPaint() {
        rectPaint.isAntiAlias = true
        rectPaint.isDither = true
        rectPaint.color = Color.RED

        textPaint.isAntiAlias = true
        textPaint.isDither = true
        textPaint.color = Color.WHITE
        textPaint.textSize = DensityUtil.dp2px(20).toFloat()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        Log.d("Shelter", "SingerItemDecoration onDraw: c:$c")
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val childAdapterPosition = parent.getChildAdapterPosition(child)
            Log.d(
                "Shelter",
                "SingerItemDecoration onDraw: i:$i childAdapterPosition:$childAdapterPosition"
            )
            val adapter = parent.adapter as SingerAdapter
            val isGroupHead = adapter.isGroupHead(childAdapterPosition)
            //四点左边
            val left = parent.left
            val right = parent.width - parent.paddingRight
            if (isGroupHead && child.top - groupHeadHeight > parent.paddingTop) {
                val top = child.top - groupHeadHeight
                val bottom = child.top
                rectPaint.color = Color.RED
                Log.d("Shelter", "SingerItemDecoration onDraw: top:$top bottom:$bottom")
                c.drawRect(
                    left.toFloat(), top, right.toFloat(),
                    bottom.toFloat(), rectPaint
                )
                val provinceName = adapter.data[childAdapterPosition].province
                textPaint.getTextBounds(provinceName, 0, provinceName.length - 1, textRect)
                val y = child.top - groupHeadHeight / 2 + textRect.height() / 2
                c.drawText(provinceName, textPaddingLeft, y, textPaint)
            } else {
                val top = child.top - 5
                val bottom = child.bottom
                rectPaint.color = Color.WHITE
                c.drawRect(
                    left.toFloat(), top.toFloat(), right.toFloat(),
                    bottom.toFloat(), rectPaint
                )
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //绘制吸顶的header
        val layoutManager = parent.layoutManager as LinearLayoutManager
        val adapter = parent.adapter as SingerAdapter
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val left = parent.paddingLeft.toFloat()
        val right: Float = (parent.width - parent.paddingRight).toFloat()
        rectPaint.color = Color.RED
        val provinceName = adapter.data[firstVisibleItemPosition].province
        textPaint.getTextBounds(provinceName, 0, provinceName.length - 1, textRect)
        val groupHead = adapter.isGroupHead(firstVisibleItemPosition + 1)
        if (groupHead) {
            val nextChild = layoutManager.findViewByPosition(firstVisibleItemPosition + 1)
            if (nextChild != null) {
                val bottom = nextChild.top.toFloat() - groupHeadHeight - 5
                val top = bottom - groupHeadHeight
                c.save()
                c.clipRect(left, parent.paddingTop.toFloat(), right, bottom)
                c.drawRect(left, top, right, bottom, rectPaint)
                val y = bottom - groupHeadHeight / 2 + textRect.height() / 2
                c.drawText(provinceName, textPaddingLeft, y, textPaint)
                c.restore()
            }
        } else {
            val top = parent.paddingTop.toFloat()
            val bottom = top + groupHeadHeight
            c.drawRect(left, top, right, bottom, rectPaint)

            c.drawText(
                provinceName,
                textPaddingLeft,
                (top + groupHeadHeight / 2 + textRect.height() / 2),
                textPaint
            )
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val adapter = parent.adapter as SingerAdapter
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        //是否为头部
        val isGroupHead = adapter.isGroupHead(childAdapterPosition)

        if (isGroupHead) {
            outRect.set(0, groupHeadHeight.toInt(), 0, 0)
        } else {
            outRect.set(0, 5, 0, 0)
        }

    }

}