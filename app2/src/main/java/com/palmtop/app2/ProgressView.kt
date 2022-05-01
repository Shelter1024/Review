package com.palmtop.app2

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.shelter.commonlib.utils.DensityUtil

/**
 * 自定义进度条 文本和进度颜色渐变
 * @author: Shelter
 * Create time: 2022/5/1, 10:19.
 */
class ProgressView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    var mProgress = 0
    var maxProgress = 100
    val RADIUS = DensityUtil.dp2px(context, 10).toFloat()
    var needDrawRightText : Boolean = true
    val path = Path()
    val rectF = RectF()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var newWidthMeasureSpec = widthMeasureSpec
        var newHeightMeasureSpec = heightMeasureSpec
        if (widthMode == MeasureSpec.AT_MOST) {
            val width = DensityUtil.dp2px(context, 180)
            Log.d("Shelter", "ProgressView onMeasure: newWidth:$width")
            newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY)
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            val height = DensityUtil.dp2px(context, 40)
            Log.d("Shelter", "ProgressView onMeasure: newHeight:$height")
            newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        setMeasuredDimension(newWidthMeasureSpec, newHeightMeasureSpec)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        initPaint()
    }

    private fun initPaint() {
        strokePaint.color = Color.BLUE
        strokePaint.style = Paint.Style.STROKE
        strokePaint.strokeWidth = DensityUtil.dp2px(context, 3).toFloat()

        progressPaint.color = Color.parseColor("#00b9ff")
        progressPaint.style = Paint.Style.FILL

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = DensityUtil.dp2px(context, 20).toFloat()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        needDrawRightText = true
        drawRoundRect(canvas)
        drawProgress(canvas)
        drawLeftText(canvas)
        drawRightText(canvas)
    }

    private fun drawRightText(canvas: Canvas?) {
        if (!needDrawRightText) return
        val text = "进度：${(getPercent() * 100).toInt()}%"
        val progressRight = width * getPercent() - paddingRight
        val textWidth = textPaint.measureText(text)
        val left = width / 2 - textWidth / 2
        val fontMetrics = textPaint.fontMetrics
        val baseLine = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2
        textPaint.color = Color.DKGRAY
        canvas?.save()
        canvas?.clipRect(progressRight.toInt(), 0, width, height)
        canvas?.drawText(text, left, baseLine, textPaint)
        canvas?.restore()
    }

    private fun drawLeftText(canvas: Canvas?) {
        val text = "进度：${(getPercent() * 100).toInt()}%"
        val progressRight = width * getPercent() - paddingRight
        val textWidth = textPaint.measureText(text)
        val left = width / 2 - textWidth / 2
        val fontMetrics = textPaint.fontMetrics
        val baseLine = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2
        textPaint.color = Color.DKGRAY
        if (progressRight <= left) {
            needDrawRightText = false
            canvas?.drawText(text, left, baseLine, textPaint)
            return
        }
        canvas?.save()
        textPaint.color = Color.WHITE
        canvas?.clipRect(0, 0, progressRight.toInt(), height)
        canvas?.drawText(text, left, baseLine, textPaint)
        canvas?.restore()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawProgress(canvas: Canvas?) {
        path.reset()
        val padding = DensityUtil.dp2px(context, 2)
        val left = 0 + paddingLeft + padding
        val right = left + (width - paddingRight * 2- padding * 2) * getPercent()
        val top = paddingTop + padding
        val bottom = height - paddingBottom - padding
        canvas?.save()
        progressPaint.strokeJoin = Paint.Join.ROUND
        rectF.set(left.toFloat(), top.toFloat(), right.toFloat(),  bottom.toFloat())
        path.addRoundRect(rectF, RADIUS, RADIUS, Path.Direction.CW)
        canvas?.clipPath(path)
        canvas?.drawRect(rectF, progressPaint)
        canvas?.restore()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawRoundRect(canvas: Canvas?) {
        val left = 0 + paddingLeft
        val right = width - paddingRight
        val top = paddingTop
        val bottom = height - paddingBottom
        canvas?.drawRoundRect(left.toFloat(), top.toFloat(), right.toFloat(),  bottom.toFloat(), RADIUS, RADIUS, strokePaint)
    }

    fun setProgress(progress: Int) {
        mProgress = progress
        invalidate()
    }

    fun getProgress() = mProgress

    fun getPercent(): Float = 1.0F * mProgress / maxProgress
}