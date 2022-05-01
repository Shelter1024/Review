package com.palmtop.app2.view

import android.graphics.*
import android.graphics.drawable.Drawable
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author: Shelter
 * Create time: 2022/5/1, 17:04.
 */
class FishDrawable() : Drawable() {
    private val otherAlpha = 110
    private val bodyAlpha = 180
    private val paint = Paint()
    private val path = Path()
    private val centerPoint = PointF()
    private var headPoint = PointF()
    private val headRadius = 100F
    private val fishMainAngle = 0F
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

    init {
        initPaint()
    }

    private fun initPaint() {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.setARGB(otherAlpha, 244, 92, 71)

        centerPoint.set(4.19F * headRadius, 4.19F * headRadius)
    }

    override fun draw(canvas: Canvas) {
        paint.alpha = otherAlpha
        //画头
        drawHead(canvas)
        //画鱼鳍
        drawFins(canvas)
        //画圆&画梯形&画三角形
        drawCircle(canvas)
        //画身体
        drawBody(canvas)
    }

    private fun drawBody(canvas: Canvas) {
        val leftTopPoint = calculatePoint(headPoint, headRadius, (fishMainAngle + 90).toDouble())
        val rightTopPoint = calculatePoint(headPoint, headRadius, (fishMainAngle - 90).toDouble())
        val bigCirclePoint = calculatePoint(headPoint, bodyLength, (fishMainAngle - 180).toDouble())
        val leftBottomPoint = calculatePoint(bigCirclePoint, bigCircleRadius, (fishMainAngle + 90).toDouble())
        val rightBottomPoint = calculatePoint(bigCirclePoint, bigCircleRadius, (fishMainAngle - 90).toDouble())
        //计算贝塞尔曲线。可通过网站生成 https://cubic-bezier.com/#.17,.67,.83,.67
        val controlPoint1 = calculatePoint(leftTopPoint, bodyLength * 0.56F,
            (fishMainAngle + 160).toDouble()
        )
        val controlPoint2 = calculatePoint(rightTopPoint, bodyLength * 0.56F,
            (fishMainAngle - 160).toDouble()
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

    private fun drawTriangle(canvas: Canvas, startPoint: PointF) {
        val triangleCenterPoint1 =
            calculatePoint(startPoint, findTriangleLength, (fishMainAngle - 180).toDouble())
        val leftPoint1 = calculatePoint(triangleCenterPoint1, bigCircleRadius, (fishMainAngle + 90).toDouble())
        val rightPoint1 = calculatePoint(triangleCenterPoint1, bigCircleRadius, (fishMainAngle - 90).toDouble())
        path.reset()
        path.moveTo(startPoint.x, startPoint.y)
        path.lineTo(leftPoint1.x, leftPoint1.y)
        path.lineTo(rightPoint1.x, rightPoint1.y)
        path.lineTo(startPoint.x, startPoint.y)
        canvas.drawPath(path, paint)

        val triangleCenterPoint2 =
            calculatePoint(startPoint, findTriangleLength - 10, (fishMainAngle - 180).toDouble())
        val leftPoint2 = calculatePoint(triangleCenterPoint2, bigCircleRadius - 20, (fishMainAngle + 90).toDouble())
        val rightPoint2 = calculatePoint(triangleCenterPoint2, bigCircleRadius - 20, (fishMainAngle - 90).toDouble())
        path.reset()
        path.moveTo(startPoint.x, startPoint.y)
        path.lineTo(leftPoint2.x, leftPoint2.y)
        path.lineTo(rightPoint2.x, rightPoint2.y)
        path.lineTo(startPoint.x, startPoint.y)
        canvas.drawPath(path, paint)
    }

    private fun drawCircle(canvas: Canvas) {
        val bigCirclePoint = calculatePoint(headPoint, bodyLength, (fishMainAngle - 180).toDouble())
        val midCirclePoint =
            calculatePoint(bigCirclePoint, findMidCircleLength, (fishMainAngle - 180).toDouble())
        val smallCirclePoint =
            calculatePoint(midCirclePoint, findSmallCircleLength, (fishMainAngle - 180).toDouble())

        canvas.drawCircle(bigCirclePoint.x, bigCirclePoint.y, bigCircleRadius, paint)
        canvas.drawCircle(midCirclePoint.x, midCirclePoint.y, midCircleRadius, paint)
        canvas.drawCircle(smallCirclePoint.x, smallCirclePoint.y, smallCircleRadius, paint)

        //画梯形
        drawTrapezoid(canvas, bigCirclePoint, midCirclePoint, bigCircleRadius, midCircleRadius)
        drawTrapezoid(canvas, midCirclePoint, smallCirclePoint, midCircleRadius, smallCircleRadius)

        //画三角形
        drawTriangle(canvas, midCirclePoint)
    }

    private fun drawTrapezoid(
        canvas: Canvas,
        startPoint: PointF,
        endPoint: PointF,
        bigRadius: Float,
        smallRadius: Float
    ) {
        val leftTopPoint = calculatePoint(startPoint, bigRadius, (fishMainAngle + 90).toDouble())
        val rightTopPoint = calculatePoint(startPoint, bigRadius, (fishMainAngle - 90).toDouble())
        val leftBottomPoint = calculatePoint(endPoint, smallRadius, (fishMainAngle + 90).toDouble())
        val rightBottomPoint =
            calculatePoint(endPoint, smallRadius, (fishMainAngle - 90).toDouble())
        path.reset()
        path.moveTo(leftTopPoint.x, leftTopPoint.y)
        path.lineTo(leftBottomPoint.x, leftBottomPoint.y)
        path.lineTo(rightBottomPoint.x, rightBottomPoint.y)
        path.lineTo(rightTopPoint.x, rightTopPoint.y)
        path.lineTo(leftTopPoint.x, leftTopPoint.y)
        canvas.drawPath(path, paint)
    }

    private fun drawFins(canvas: Canvas) {
        val rightFinsPoint = calculatePoint(
            headPoint, findFinsLength,
            (fishMainAngle - 110).toDouble()
        )
        val rightEndPoint = calculatePoint(
            rightFinsPoint, finsLength,
            (fishMainAngle - 180).toDouble()
        )
        val rightControlPoint = calculatePoint(
            rightFinsPoint, finsLength * 1.8F,
            (fishMainAngle - 115).toDouble()
        )
        path.reset()
        path.moveTo(rightFinsPoint.x, rightFinsPoint.y)
        path.quadTo(rightControlPoint.x, rightControlPoint.y, rightEndPoint.x, rightEndPoint.y)
        canvas.drawPath(path, paint)

        val leftFinsPoint = calculatePoint(
            headPoint, findFinsLength,
            (fishMainAngle + 110).toDouble()
        )
        val leftEndPoint = calculatePoint(
            leftFinsPoint, finsLength,
            (fishMainAngle + 180).toDouble()
        )
        val leftControlPoint = calculatePoint(
            leftFinsPoint, finsLength * 1.8F,
            (fishMainAngle + 115).toDouble()
        )
        path.reset()
        path.moveTo(leftFinsPoint.x, leftFinsPoint.y)
        path.quadTo(leftControlPoint.x, leftControlPoint.y, leftEndPoint.x, leftEndPoint.y)
        canvas.drawPath(path, paint)
    }

    private fun drawHead(canvas: Canvas) {
        headPoint = calculatePoint(centerPoint, bodyLength / 2, fishMainAngle.toDouble())
        canvas.drawCircle(headPoint.x, headPoint.y, headRadius, paint)
    }

    private fun calculatePoint(startPoint: PointF, length: Float, angle: Double): PointF {
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
}