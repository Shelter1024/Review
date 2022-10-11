package com.example.testcalendar

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator

/**
 * @author: Shelter
 * Create time: 2022/9/21, 15:37.
 */
class ComputingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    val baseGoldCount = 50
    val textBound = Rect()
    val bigTextSize = DensityUtil.dp2px(context, 40f)
    val smallTextSize = DensityUtil.dp2px(context, 30f)

    init {
        textPaint.color = Color.parseColor("#FF463E")
        textPaint.textSize = DensityUtil.dp2px(context, 40f).toFloat()
    }

    var goldCount = 50

    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }

        val goldText = "$goldCount"
        textPaint.textSize
        val textWidth = textPaint.measureText(goldText)
        textPaint.fontMetrics
        textPaint.textAlign = Paint.Align.RIGHT
        textPaint.getTextBounds(goldText, 0, goldText.length - 1, textBound)
        val x = width
        val y = height / 2 - (textPaint.fontMetrics.ascent + textPaint.fontMetrics.descent) / 2
        canvas.drawText(goldText, x.toFloat(), y, textPaint)
        textPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText("+", width -  textWidth, y, textPaint)
    }


    fun startAnim(maxGoldCount: Int) {
        val animator1 = ValueAnimator.ofInt(0, baseGoldCount)
        animator1.interpolator = AccelerateDecelerateInterpolator()
        animator1.duration = 3000
        animator1.addUpdateListener {
            goldCount = it.animatedValue as Int
            invalidate()
        }
        val animator2 = ValueAnimator.ofInt(baseGoldCount, maxGoldCount)
        animator2.addUpdateListener {
            goldCount = it.animatedValue as Int
            invalidate()
        }
        animator2.interpolator = AccelerateInterpolator()
        animator2.duration = 3000
        animator2.startDelay = 500

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(animator1, animator2)
        animatorSet.start()
    }


}