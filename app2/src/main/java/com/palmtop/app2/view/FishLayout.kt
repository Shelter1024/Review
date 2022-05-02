package com.palmtop.app2.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.palmtop.app2.FishActivity
import com.palmtop.app2.centerPoint
import com.palmtop.app2.headPoint
import com.palmtop.app2.headRadius
import com.shelter.commonlib.utils.DensityUtil
import org.jetbrains.anko.wrapContent
import kotlin.math.atan2
import kotlin.math.sqrt
import kotlin.properties.Delegates

/**
 * @author: Shelter
 * Create time: 2022/5/2, 12:41.
 */
class FishLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val ripplePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var touchX: Float = 0F
    private var touchY: Float = 0F
    private var ripple = 0F
    private var rippleAlpha = 0
    private var fishDrawable: FishDrawable by Delegates.notNull()
    private var ivFish: ImageView by Delegates.notNull()

    init {
        ripplePaint.isDither = true
        ripplePaint.style = Paint.Style.STROKE
        ripplePaint.color = Color.parseColor("#00b9ff")
        ripplePaint.strokeWidth = DensityUtil.dp2px(context, 2).toFloat()
        setWillNotDraw(false)
        initAnim()
    }

    private fun initAnim() {
        fishDrawable = FishDrawable()
        val layoutParams = LayoutParams(wrapContent, wrapContent)
        ivFish = ImageView(context)
        ivFish.setImageDrawable(fishDrawable)
        addView(ivFish, layoutParams)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d("Shelter", "FishLayout onTouchEvent: action: ${event?.action}")

        touchX = event?.x ?: 0F
        touchY = event?.y ?: 0F

        startRippleAnim()

        startFishAnim()

        (context as FishActivity).playClickSound()
        return super.onTouchEvent(event)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startFishAnim() {
        //鱼的相对中心点
        val fishRelativeCenter = fishDrawable.centerPoint
        //鱼的绝对中心点
        val middlePoint = PointF(ivFish.x + fishRelativeCenter.x, ivFish.y + fishRelativeCenter.y)
        //鱼头的绝对中心点
        val headPoint =
            PointF(ivFish.x + fishDrawable.headPoint.x, ivFish.y + fishDrawable.headPoint.y)
        //触摸点
        val endPoint = PointF(touchX, touchY)

        //计算控制点
        //三界贝塞尔曲线 需要四个点 起点、终点、两个控制点
        //本例中 起点为鱼的重心，结束点为触摸点，鱼头的中心是一个控制点，只需要计算出另外一个控制点
        //获取夹角 两角相加即为AOB角度（已考虑正负）
        val OABAngle = includeAngle(middlePoint, headPoint, endPoint) / 2
        val BOXAngle =
            includeAngle(middlePoint, PointF(middlePoint.x + 1, middlePoint.y), headPoint)
        val controlPoint = fishDrawable.calculatePoint(
            middlePoint, fishDrawable.headRadius * 1.6F,
            (OABAngle + BOXAngle).toDouble()
        )

        val path = Path()
        path.moveTo(middlePoint.x - fishRelativeCenter.x, middlePoint.y - fishRelativeCenter.y)
        path.cubicTo(
            headPoint.x - fishRelativeCenter.x,
            headPoint.y - fishRelativeCenter.y,
            controlPoint.x - fishRelativeCenter.x,
            controlPoint.y - fishRelativeCenter.y,
            endPoint.x - fishRelativeCenter.x,
            endPoint.y - fishRelativeCenter.y
        )

        val animator = ObjectAnimator.ofFloat(ivFish, "x", "y", path)
        animator.duration = 1500
        //动画过程中改变小鱼摆动的频率
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                fishDrawable.setFrequency(1f)
            }

            override fun onAnimationStart(animation: Animator?) {
                fishDrawable.setFrequency(3f)
            }
        })
        animator.addUpdateListener {
            val pathMeasure = PathMeasure(path, false)
            val array = FloatArray(2)
            val distance = pathMeasure.length * it.animatedFraction
            pathMeasure.getPosTan(distance, null, array)
            val angle = Math.toDegrees(atan2(-array[1].toDouble(), array[0].toDouble()))
            fishDrawable.setFishMainAngle(angle.toFloat())
        }
        animator.start()
    }

    override fun onDraw(canvas: Canvas?) {
        if (this.ripple != 0f && touchX != 0F) {
            ripplePaint.alpha = rippleAlpha
            canvas?.drawCircle(touchX, touchY, ripple, ripplePaint)
        }
    }

    fun includeAngle(O: PointF, A: PointF, B: PointF): Float {
        // cosAOB
        // OA*OB=(Ax-Ox)(Bx-Ox)+(Ay-Oy)*(By-Oy)
        val AOB = (A.x - O.x) * (B.x - O.x) + (A.y - O.y) * (B.y - O.y)
        val OALength =
            sqrt(((A.x - O.x) * (A.x - O.x) + (A.y - O.y) * (A.y - O.y)).toDouble()).toFloat()
        // OB 的长度
        val OBLength =
            sqrt(((B.x - O.x) * (B.x - O.x) + (B.y - O.y) * (B.y - O.y)).toDouble()).toFloat()
        val cosAOB = AOB / (OALength * OBLength)

        // 反余弦
        val angleAOB = Math.toDegrees(Math.acos(cosAOB.toDouble())).toFloat()

        // AB连线与X的夹角的tan值 - OB与x轴的夹角的tan值
        val direction = (A.y - B.y) / (A.x - B.x) - (O.y - B.y) / (O.x - B.x)
        return if (direction == 0f) {
            if (AOB >= 0) {
                0F
            } else {
                180F
            }
        } else {
            if (direction > 0) {
                -angleAOB
            } else {
                angleAOB
            }
        }
    }

    private fun startRippleAnim() {
        val animator = ObjectAnimator.ofFloat(this, "ripple", 0f, 150F)
        animator.duration = 1000
        animator.start()
    }

    public fun setRipple(ripple: Float) {
        this.ripple = ripple
        this.rippleAlpha = (150 - ripple).toInt()
        invalidate()
    }

    public fun getRipple() = ripple
}