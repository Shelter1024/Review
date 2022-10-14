package com.palmtop.app2

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.animation.doOnEnd
import com.shelter.commonlib.utils.DensityUtil.dp2px

/**
 * @author: Shelter
 * Create time: 2022/9/25, 18:27.
 */
class CloseView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val size = dp2px(28f)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val closeBitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_pop_up_window_cancel)
    private val textSize = dp2px(20f).toFloat()
    private var timeEnd: Boolean = false
    private var curSecond: Int = 1
    private val rect = Rect()

    init {
        textPaint.textSize = textSize
        textPaint.color = Color.parseColor("#666666")
    }

    override fun onDraw(canvas: Canvas?) {
        //view设置invisible或者gone后，不绘制
        Log.d("Shelter", "CloseView onDraw")
        if (canvas == null) {
            return
        }
        if (!timeEnd) {
            val text = "${curSecond}s"
            val textWidth = textPaint.measureText(text)
            val x = width / 2 - textWidth / 2
            val fontMetrics = textPaint.fontMetrics
            val y = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2
            canvas.drawText(text, x, y, textPaint)
        } else {
            rect.set(0, 0, width, height)
            canvas.drawBitmap(closeBitmap, null, rect, bitmapPaint)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //view设置gone后不测量，设置invisible还会测量
        Log.d("Shelter", "CloseView onMeasure")
        setMeasuredDimension(size, size)
    }


    fun startTimer(seconds: Int) {
        timeEnd = false
        val animator = ValueAnimator.ofInt(seconds, 0)
        animator.interpolator = LinearInterpolator()
        animator.doOnEnd {
            timeEnd = true
            isEnabled = true
        }
        animator.addUpdateListener {
            curSecond = it.animatedValue as Int
            Log.d("Shelter", "CloseView startTimer onUpdate width:${measuredWidth}, height:${measuredHeight}")
            invalidate()
        }
        animator.duration = (seconds * 1000).toLong()
        animator.start()
        isEnabled = false
    }


}