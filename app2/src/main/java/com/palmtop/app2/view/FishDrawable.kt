package com.palmtop.app2.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.animation.LinearInterpolator
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author: Shelter
 * Create time: 2022/5/1, 17:04.
 */
class FishDrawable : Drawable() {
    private val otherAlpha = 110
    private val bodyAlpha = 180
    private val paint = Paint()
    private val eyePaint = Paint()
    private val path = Path()
    private val centerPoint = PointF()
    private var headPoint = PointF()
    private val headRadius = 50F
    private var fishMainAngle = 90F
    private val bodyLength = 3.2F * headRadius
    private val findFinsLength = 0.9F * headRadius
    private val finsLength = 1.3F * headRadius

    // 大圆的半径
    private val bigCircleRadius: Float = 0.7f * headRadius

    // 中圆的半径
    private val midCircleRadius = 0.6f * bigCircleRadius

    // 小圆半径
    private val smallCircleRadius = 0.4f * midCircleRadius

    // --寻找尾部中圆圆心的线长
    private val findMidCircleLength = bigCircleRadius * (0.6f + 1)

    // --寻找尾部小圆圆心的线长
    private val findSmallCircleLength = midCircleRadius * (0.4f + 2.7f)

    // --寻找大三角形底边中心点的线长
    private val findTriangleLength = midCircleRadius * 2.7f

    // 寻找鱼眼的长度
    private val findEyeLength: Float = 0.6f * headRadius
    private val eyeRadius = 5F
    private var currentValue: Float = 0F
    private var frequency: Float = 1F

    init {
        initPaint()
        val animator = ObjectAnimator.ofFloat(this, "currentValue", 0F, 3600F)
        animator.duration = 15000
        animator.repeatMode = ValueAnimator.RESTART
        animator.repeatCount = ValueAnimator.INFINITE
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener {
//            Log.d("Shelter", "FishActivity onCreate: animatedValue: ${it.animatedValue}")
        }
        animator.start()
    }

    private fun initPaint() {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.setARGB(otherAlpha, 244, 92, 71)

        eyePaint.isAntiAlias = true
        eyePaint.isDither = true
        eyePaint.color = Color.DKGRAY
        eyePaint.style = Paint.Style.FILL

        centerPoint.set(4.19F * headRadius, 4.19F * headRadius)
    }

    override fun draw(canvas: Canvas) {
        paint.alpha = otherAlpha
        val fishAngle = fishMainAngle + sin(Math.toRadians((currentValue * 1.2F * frequency).toDouble())) * 10
        //画头
        drawHead(canvas, fishAngle)
        //画鱼眼
        drawEye(canvas, fishAngle)
        //画鱼鳍
        drawFins(canvas, fishAngle)
        //画圆&画梯形&画三角形
        drawCircle(canvas, fishAngle)
        //画身体
        drawBody(canvas, fishAngle)
    }

    private fun drawBody(canvas: Canvas, fishAngle: Double) {
        val leftTopPoint = calculatePoint(headPoint, headRadius, fishAngle + 90)
        val rightTopPoint = calculatePoint(headPoint, headRadius, fishAngle - 90)
        val bigCirclePoint = calculatePoint(headPoint, bodyLength, fishAngle - 180)
        val leftBottomPoint = calculatePoint(bigCirclePoint, bigCircleRadius, fishAngle + 90)
        val rightBottomPoint = calculatePoint(bigCirclePoint, bigCircleRadius, fishAngle - 90)
        //计算贝塞尔曲线。可通过网站生成 https://cubic-bezier.com/#.17,.67,.83,.67
        val controlPoint1 = calculatePoint(leftTopPoint, bodyLength * 0.56F,
            fishAngle + 170
        )
        val controlPoint2 = calculatePoint(rightTopPoint, bodyLength * 0.56F,
            fishAngle - 170
        )
        //画左侧躯干
        paint.alpha = bodyAlpha
        path.reset()
        path.moveTo(leftTopPoint.x, leftTopPoint.y)
        path.quadTo(controlPoint1.x, controlPoint1.y, leftBottomPoint.x, leftBottomPoint.y)
        path.lineTo(rightBottomPoint.x, rightBottomPoint.y)
        path.quadTo(controlPoint2.x, controlPoint2.y, rightTopPoint.x, rightTopPoint.y)
        canvas.drawPath(path, paint)
    }

    private fun drawTriangle(canvas: Canvas, startPoint: PointF, fishAngle: Double) {
        val findEdgeLength: Float = (abs(sin(Math.toRadians(currentValue * 1.5 * frequency)))).toFloat() * bigCircleRadius
        val triangleAngle = fishAngle + sin(Math.toRadians(currentValue * 1.5 * frequency)) * 35
        val triangleCenterPoint1 =
            calculatePoint(startPoint, findTriangleLength, triangleAngle - 180)
        val leftPoint1 = calculatePoint(triangleCenterPoint1, findEdgeLength, triangleAngle + 90)
        val rightPoint1 = calculatePoint(triangleCenterPoint1, findEdgeLength, triangleAngle - 90)
        path.reset()
        path.moveTo(startPoint.x, startPoint.y)
        path.lineTo(leftPoint1.x, leftPoint1.y)
        path.lineTo(rightPoint1.x, rightPoint1.y)
        canvas.drawPath(path, paint)

        val triangleCenterPoint2 =
            calculatePoint(startPoint, findTriangleLength - 10, triangleAngle - 180)
        val leftPoint2 = calculatePoint(triangleCenterPoint2, findEdgeLength - 20, triangleAngle + 90)
        val rightPoint2 = calculatePoint(triangleCenterPoint2, findEdgeLength - 20, triangleAngle - 90)
        path.reset()
        path.moveTo(startPoint.x, startPoint.y)
        path.lineTo(leftPoint2.x, leftPoint2.y)
        path.lineTo(rightPoint2.x, rightPoint2.y)
        canvas.drawPath(path, paint)
    }

    private fun drawCircle(canvas: Canvas, fishAngle: Double) {
        //画梯形
        val newAngle1 = fishAngle + cos(Math.toRadians((currentValue * 1.5F).toDouble())) * 15
        val bigCirclePoint = calculatePoint(headPoint, bodyLength, fishAngle - 180)
        val midCirclePoint =
            calculatePoint(bigCirclePoint, findMidCircleLength, newAngle1 - 180)
        canvas.drawCircle(bigCirclePoint.x, bigCirclePoint.y, bigCircleRadius, paint)
        canvas.drawCircle(midCirclePoint.x, midCirclePoint.y, midCircleRadius, paint)
        //节肢1
        drawTrapezoid(canvas, bigCirclePoint, midCirclePoint, bigCircleRadius, midCircleRadius, newAngle1)
        //节肢2
        val newAngle2 = fishAngle + sin(Math.toRadians((currentValue * 1.5F).toDouble())) * 35
        val smallCirclePoint =
            calculatePoint(midCirclePoint, findSmallCircleLength, newAngle2 - 180)
        canvas.drawCircle(smallCirclePoint.x, smallCirclePoint.y, smallCircleRadius, paint)
        drawTrapezoid(canvas, midCirclePoint, smallCirclePoint, midCircleRadius, smallCircleRadius, newAngle2)

        //画三角形
        drawTriangle(canvas, midCirclePoint, fishAngle)
    }

    private fun drawTrapezoid(
        canvas: Canvas,
        startPoint: PointF,
        endPoint: PointF,
        bigRadius: Float,
        smallRadius: Float,
        fishAngle: Double
    ) {
        val leftTopPoint = calculatePoint(startPoint, bigRadius, fishAngle + 90)
        val rightTopPoint = calculatePoint(startPoint, bigRadius, fishAngle - 90)
        val leftBottomPoint = calculatePoint(endPoint, smallRadius, fishAngle + 90)
        val rightBottomPoint =
            calculatePoint(endPoint, smallRadius, fishAngle - 90)
        path.reset()
        path.moveTo(leftTopPoint.x, leftTopPoint.y)
        path.lineTo(leftBottomPoint.x, leftBottomPoint.y)
        path.lineTo(rightBottomPoint.x, rightBottomPoint.y)
        path.lineTo(rightTopPoint.x, rightTopPoint.y)
        path.lineTo(leftTopPoint.x, leftTopPoint.y)
        canvas.drawPath(path, paint)
    }

    private fun drawFins(canvas: Canvas, fishAngle: Double) {
        val finsControlAngle = 115 + sin(currentValue * 1.5F * frequency) * 5
        val rightFinsPoint = calculatePoint(
            headPoint, findFinsLength,
            (fishAngle - 110)
        )
        val rightEndPoint = calculatePoint(
            rightFinsPoint, finsLength,
            (fishAngle - 180)
        )
        val rightControlPoint = calculatePoint(
            rightFinsPoint, finsLength * 1.8F,
            (fishAngle - finsControlAngle)
        )
        path.reset()
        path.moveTo(rightFinsPoint.x, rightFinsPoint.y)
        path.quadTo(rightControlPoint.x, rightControlPoint.y, rightEndPoint.x, rightEndPoint.y)
        canvas.drawPath(path, paint)

        val leftFinsPoint = calculatePoint(
            headPoint, findFinsLength,
            (fishAngle + 110)
        )
        val leftEndPoint = calculatePoint(
            leftFinsPoint, finsLength,
            (fishAngle + 180)
        )
        val leftControlPoint = calculatePoint(
            leftFinsPoint, finsLength * 1.8F,
            (fishAngle + finsControlAngle)
        )
        path.reset()
        path.moveTo(leftFinsPoint.x, leftFinsPoint.y)
        path.quadTo(leftControlPoint.x, leftControlPoint.y, leftEndPoint.x, leftEndPoint.y)
        canvas.drawPath(path, paint)
    }

    private fun drawEye(canvas: Canvas, fishAngle: Double) {
        val leftEyePoint = calculatePoint(headPoint, findEyeLength, fishAngle + 45)
        val rightEyePoint = calculatePoint(headPoint, findEyeLength, fishAngle - 45)
        canvas.drawCircle(leftEyePoint.x, leftEyePoint.y, eyeRadius, eyePaint)
        canvas.drawCircle(rightEyePoint.x, rightEyePoint.y, eyeRadius, eyePaint)
    }

    private fun drawHead(canvas: Canvas, fishAngle: Double) {
        headPoint = calculatePoint(centerPoint, bodyLength / 2, fishAngle)
//        headPoint = calculatePoint(centerPoint, bodyLength / 2, fishAngle)
        canvas.drawCircle(headPoint.x, headPoint.y, headRadius, paint)
    }

    fun calculatePoint(startPoint: PointF, length: Float, angle: Double): PointF {
        val deltaX = cos(Math.toRadians(angle)) * length
        val deltaY = sin(Math.toRadians(angle - 180)) * length
        return PointF((startPoint.x + deltaX).toFloat(), (startPoint.y + deltaY).toFloat())
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return (8.38F * headRadius).toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return (8.38F * headRadius).toInt()
    }

    fun setCurrentValue(value: Float) {
        currentValue = value
        invalidateSelf()
    }

    fun getCurrentValue() = currentValue

    fun getCenterPoint() = centerPoint

    fun getHeadPoint() = headPoint

    fun getHeadRadius() = headRadius

    fun setFrequency(frequency: Float) {
        this.frequency = frequency
    }

    fun setFishMainAngle(angle: Float) {
        fishMainAngle = angle
    }
}