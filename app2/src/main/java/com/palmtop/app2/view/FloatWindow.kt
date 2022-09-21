package com.taoliu.news.modules.home.ui.widget

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import com.palmtop.app2.view.CountdownTimerView
import java.lang.ref.WeakReference

/**
 * @author: Shelter
 * Create time: 2022/9/14, 14:21.
 */
class FloatWindow(activity: Activity) {

    private var windowManager: WindowManager
    private var layoutInflater: LayoutInflater
    private lateinit var mFloatingLayout: View
    private lateinit var timerView: CountdownTimerView
    private var activityReference: WeakReference<Activity>

    init {
        windowManager =
            activity.applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutInflater = LayoutInflater.from(activity.applicationContext)
        activityReference = WeakReference(activity)
    }

    private val onTurnACircleEndListener = object : CountdownTimerView.OnTurnACircleEndListener {
        override fun onCircleTurnEnd() {
            Log.d("Shelter", "FloatWindow onCircleTurnEnd")
        }
    }

    private val onClickListener = View.OnClickListener { Log.d("Shelter", "FloatWindow onClick") }

//    fun show(timerInfo: TimerInfo) {
//        val activity = activityReference.get()
//        if (activity == null || activity.isFinishing) {
//            return
//        }
//        val contentLayout: ViewGroup = activity.findViewById(android.R.id.content)
//        // 获取浮动窗口视图所在布局
//        mFloatingLayout = layoutInflater.inflate(R.layout.layout_float_window, null)
//        val lp = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        lp.gravity = Gravity.RIGHT or Gravity.BOTTOM
//        lp.rightMargin = dp2px(10f)
//        lp.bottomMargin = dp2px(50f)
//        contentLayout.addView(mFloatingLayout, lp)
//        timerView = mFloatingLayout.findViewById(R.id.timerView)
//        timerView.updateText(timerInfo.round, timerInfo.allRound)
//        timerView.setOnTurnACircleEndListener(onTurnACircleEndListener)
//        //悬浮框触摸事件，设置悬浮框可拖动
//        mFloatingLayout.setOnTouchListener(FloatingListener())
//        mFloatingLayout.setOnClickListener(onClickListener)
//        startTimer()
//    }


    fun dismiss() {
        val activity = activityReference.get()
        if (activity != null && !activity.isFinishing) {
            val contentLayout: ViewGroup = activity.findViewById(android.R.id.content)
            contentLayout.removeView(mFloatingLayout)
        }
    }


    //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchStartX = 0  //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchStartY = 0  //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchCurrentX = 0 //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchCurrentY = 0

    //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStartX = 0  //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStartY = 0  //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStopX = 0 //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStopY = 0
    private var downX = 0f
    private var downY = 0f
    private var endX = 0f
    private var endY = 0f

    //判断悬浮窗口是否移动，这里做个标记，防止移动后松手触发了点击事件

    inner class FloatingListener : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            Log.d("Shelter", "FloatingListener onTouch action：${event.action}")
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("Shelter", "FloatingListener onTouch down")
                    mTouchStartX = event.rawX.toInt()
                    mTouchStartY = event.rawY.toInt()
                    mStartX = event.rawX.toInt()
                    mStartY = event.rawY.toInt()
                    downX = v.x
                    downY = v.y
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("Shelter", "FloatingListener onTouch move")
                    mTouchCurrentX = event.rawX.toInt()
                    mTouchCurrentY = event.rawY.toInt()
                    v.x += mTouchCurrentX - mTouchStartX
                    v.y += mTouchCurrentY - mTouchStartY
                    mTouchStartX = mTouchCurrentX
                    mTouchStartY = mTouchCurrentY
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    Log.d("Shelter", "FloatingListener onTouch up")
                    mStopX = event.rawX.toInt()
                    mStopY = event.rawY.toInt()
                    endX = v.x
                    endY = v.y
                    Log.d("Shelter", "FloatingListener onTouch mStartX:$mStartX, mStopX:$mStopX, mStartY:$mStartY, mStopY:$mStopY")
                    if (Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1) {
                        Log.d("Shelter", "FloatingListener onTouch up 移动了")
                        startResetAnim()
                    } else {
                        Log.d("Shelter", "FloatingListener onTouch 未移动")
                        mFloatingLayout.performClick()
                    }
                }
            }

            //如果是移动事件不触发OnClick事件，防止移动的时候一放手形成点击事件
            return true
        }
    }

    private fun startResetAnim() {
        val xAnimator = ValueAnimator.ofFloat(endX, downX)
        xAnimator.interpolator = LinearInterpolator()
        xAnimator.addUpdateListener {
            val animatedValue = it.getAnimatedValue()
            mFloatingLayout.x = animatedValue as Float
        }
        xAnimator.duration = 300
        xAnimator.start()
    }

    fun startTimer() {
        timerView.startAnim()
    }

}