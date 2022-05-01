package com.palmtop.app2.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.shelter.commonlib.utils.DensityUtil

/**
 * @author: Shelter
 * Create time: 2022/4/30, 20:34.
 */
class CustomTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    private val textPaint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = DensityUtil.dp2px(context,30).toFloat()
        paint
    }
    private val linePaint by lazy {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = 30F
        paint.color = Color.RED
        paint
    }

    private var mPercent: Float = 0F

    private val text = "新年快乐"

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        Log.d("Shelter", "CustomTextView onMeasure: width: $measuredWidth, height: $measuredHeight")
    }

    override fun onDraw(canvas: Canvas?) {
        drawVerticalLine(canvas)
        drawHorizontalLine(canvas)
//        drawCenterText(canvas)
        drawLeftText(canvas)
        drawRightText(canvas)
    }

    private fun drawRightText(canvas: Canvas?) {
        canvas?.save()
        textPaint.textAlign = Paint.Align.LEFT
        val textWidth = textPaint.measureText(text)
        val left = width / 2 - textWidth / 2
        val right = left + textWidth * mPercent
        val baseLine = height / 2 - (textPaint.fontMetrics.descent + textPaint.fontMetrics.ascent) / 2
        textPaint.color = Color.BLUE
        canvas?.clipRect(right, 0F, width.toFloat(), height.toFloat())
        canvas?.drawText(text, left, baseLine, textPaint)
        canvas?.restore()
    }

    private fun drawLeftText(canvas: Canvas?) {
        canvas?.save()
        textPaint.textAlign = Paint.Align.LEFT
        val textWidth = textPaint.measureText(text)
        val left = width / 2 - textWidth / 2
        val right = left + textWidth * mPercent
        val baseLine = height / 2 - (textPaint.fontMetrics.descent + textPaint.fontMetrics.ascent) / 2
        textPaint.color = Color.RED
        canvas?.clipRect(0F, 0F, right, height.toFloat())
        canvas?.drawText(text, left, baseLine, textPaint)
        canvas?.restore()
    }

    private fun drawCenterText(canvas: Canvas?) {
        textPaint.textAlign = Paint.Align.CENTER
        val ascent = textPaint.fontMetrics.ascent
        val descent = textPaint.fontMetrics.descent
        val x = width / 2F
//        val y = height / 2F + (descent - ascent) / 2 - descent
        //简化
        val y = height / 2F - (ascent + descent) / 2
        canvas?.drawText(text, x, y, textPaint)
    }

    private fun drawHorizontalLine(canvas: Canvas?) {
        val left = 0F
        val right = width.toFloat()
        val top = height / 2F
        canvas?.drawLine(left, top, right, top, linePaint)
    }

    private fun drawVerticalLine(canvas: Canvas?) {
        val left = width / 2F
        val top = 0F
        val bottom = height.toFloat()
        canvas?.drawLine(left, top, left, bottom, linePaint)
    }

    fun setPercent(percent: Float) {
//        Log.d("Shelter", "CustomTextView setPercent: percent: $percent")
        mPercent = percent
        invalidate()
    }

    fun getPercent() : Float {
        return mPercent
    }

}