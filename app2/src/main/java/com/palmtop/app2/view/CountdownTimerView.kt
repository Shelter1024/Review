package com.palmtop.app2.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.annotation.Keep
import androidx.core.animation.doOnEnd
import com.palmtop.app2.R
import com.shelter.commonlib.utils.DensityUtil.dp2px

/**
 * @author: Shelter
 * Create time: 2022/9/14, 14:21.
 */
class CountdownTimerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {
    private val defaultWidth = dp2px(70f)
    private val defaultHeight = dp2px(97f)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val smallRadius = dp2px(30f)
    private val bigRadius = dp2px(32.5f)
    private val innerColor = Color.parseColor("#6E090909")
    private val outerColor = Color.parseColor("#33000000")

    private val strokeWidth = dp2px(5f)
    private val bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.ic_red_envelope_collection_no)
    private val smallBitmap =
        BitmapFactory.decodeResource(resources, R.drawable.ic_red_envelope_collection_small)
    private val bubbleBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_floating_red_packet_bubble)
    private var leftMargin: Int = dp2px(23f).toInt()
    private var topMargin: Int = dp2px(24f).toInt()
    private val bottomMargin = dp2px(17f)
    private val bitmapPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val arcPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectF: RectF = RectF()
    private var currentCount = 0
    private var totalCount = 6
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var onTurnACircleEndListener: OnTurnACircleEndListener? = null
    private val rect = Rect()
    var isTimerEnd: Boolean = true

    private var angle: Int = -90
        set(value) {
            field = value
            invalidate()
        }

    @Keep
    private var sweepAngle = 0

    private fun initPaint() {
        arcPaint.color = Color.parseColor("#FE1E1F")
        arcPaint.style = Paint.Style.STROKE
        arcPaint.strokeCap = Paint.Cap.ROUND
        arcPaint.strokeWidth = strokeWidth.toFloat()
        textPaint.color = Color.WHITE
        textPaint.textSize = dp2px(14f).toFloat()
        bgPaint.color = Color.parseColor("#FFA539")
    }


    override fun onDraw(canvas: Canvas?) {
        paint.color = innerColor
        paint.style = Paint.Style.FILL
        //1.画里面的圆
        canvas?.drawCircle(width / 2f, width / 2f, smallRadius.toFloat(), paint)
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = strokeWidth.toFloat()
        paint.color = outerColor
        //2.画外面的圆
        canvas?.drawCircle(width / 2f, width / 2f, bigRadius.toFloat(), paint)
        //3.画红包
        canvas?.drawBitmap(bitmap, width / 2f - leftMargin, width / 2f - topMargin, bitmapPaint)
        //4.画进度
        rectF.set(
            strokeWidth / 2f,
            strokeWidth / 2f,
            width.toFloat() - strokeWidth / 2,
            width.toFloat() - strokeWidth / 2
        )
        canvas?.drawArc(rectF, angle.toFloat(), sweepAngle.toFloat(), false, arcPaint)
        //5.画小红包
        canvas?.drawBitmap(smallBitmap, 0f, (height - bottomMargin).toFloat(), bitmapPaint)
        //6.画文字背景
        val left = dp2px(16f)
        val right = left + dp2px(45f)
        rect.set(left, height - bottomMargin, right, height)
        canvas?.drawBitmap(bubbleBitmap, null, rect, bgPaint)
        val text = "$currentCount/$totalCount"
        textPaint.measureText(text)
        val y =
            height - smallBitmap.height / 2 - (textPaint.fontMetrics.ascent + textPaint.fontMetrics.descent) / 2
        val x = dp2px(30f)
        //7.画文字
        canvas?.drawText(text, x.toFloat(), y, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val minWidth = defaultWidth
        val minHeight = defaultHeight
        var resultWidth: Int = widthSize
        var resultHeight: Int = heightSize
        if (widthMode == MeasureSpec.AT_MOST) {
            resultWidth = kotlin.math.min(widthSize, minWidth)
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            resultHeight = kotlin.math.min(heightSize, minHeight)
        }
        setMeasuredDimension(resultWidth, resultHeight)
        initPaint()
    }

    fun setSweepAngle(sweepAngle: Int) {
        this.sweepAngle = sweepAngle
        invalidate()
    }

    fun getSweepAngle() = sweepAngle


    fun startAnim() {
        if (currentCount >= totalCount || !isTimerEnd) {
            return
        }
        isTimerEnd = false
        val animator = ObjectAnimator.ofInt(this, "sweepAngle", 0, 360)
        animator.doOnEnd {
            Log.d("Shelter", "CountdownTimerView startAnim")
            isTimerEnd = true
            sweepAngle = 0
            currentCount++
            if (currentCount > totalCount) {
                currentCount = totalCount
            }
            invalidate()
            onTurnACircleEndListener?.onCircleTurnEnd()
        }
        animator.interpolator = LinearInterpolator()
        animator.duration = 10000
        animator.start()
    }

    fun updateText(curCircle: Int, totalCircle: Int) {
        currentCount = curCircle
        totalCount = totalCircle
        invalidate()
    }

    fun setOnTurnACircleEndListener(onTurnACircleEndListener: OnTurnACircleEndListener) {
        this.onTurnACircleEndListener = onTurnACircleEndListener
    }


    interface OnTurnACircleEndListener {
        fun onCircleTurnEnd()
    }


}