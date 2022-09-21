package com.palmtop.app2

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.*
import android.widget.Chronometer
import android.widget.LinearLayout

/**
 * @author: Shelter
 * Create time: 2022/9/16, 21:41.
 */
class FloatWindowService: Service() {

    private var winManager: WindowManager? = null
    private lateinit var wmParams: WindowManager.LayoutParams
    private var inflater: LayoutInflater? = null

    //浮动布局
    private var mFloatingLayout: View? = null
    private var linearLayout: LinearLayout? = null
    private val chronometer: Chronometer? = null
    private val rangeTime: Long = 0


    override fun onBind(intent: Intent?): IBinder? {
        initWindow()
        //悬浮框点击事件的处理
        initFloating()
        return MyBinder()
    }

    inner class MyBinder : Binder() {
        val service: FloatWindowService
            get() = this@FloatWindowService
    }

    override fun onCreate() {
        super.onCreate()
    }

    /**
     * 悬浮窗点击事件
     */
    private fun initFloating() {
        linearLayout = mFloatingLayout!!.findViewById(R.id.line1)
        //悬浮框触摸事件，设置悬浮框可拖动
        linearLayout?.setOnTouchListener(FloatingListener())
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

    //判断悬浮窗口是否移动，这里做个标记，防止移动后松手触发了点击事件
    private var isMove = false

    inner class FloatingListener : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("Shelter", "FloatingListener onTouch down")
                    isMove = false
                    mTouchStartX = event.rawX.toInt()
                    mTouchStartY = event.rawY.toInt()
                    mStartX = event.x.toInt()
                    mStartY = event.y.toInt()
                    return true
                }
                MotionEvent.ACTION_MOVE -> {
                    Log.d("Shelter", "FloatingListener onTouch move")
                    mTouchCurrentX = event.rawX.toInt()
                    mTouchCurrentY = event.rawY.toInt()
                    wmParams!!.x += mTouchCurrentX - mTouchStartX
                    wmParams!!.y += mTouchCurrentY - mTouchStartY
                    winManager!!.updateViewLayout(mFloatingLayout, wmParams)
                    mTouchStartX = mTouchCurrentX
                    mTouchStartY = mTouchCurrentY
                }
                MotionEvent.ACTION_UP -> {
                    Log.d("Shelter", "FloatingListener onTouch up")
                    mStopX = event.x.toInt()
                    mStopY = event.y.toInt()
                    if (Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1) {
                        isMove = true
                    }
                }
                else -> {}
            }

            //如果是移动事件不触发OnClick事件，防止移动的时候一放手形成点击事件
            return isMove
        }
    }

    /**
     * 初始化窗口
     */
    private fun initWindow() {
        winManager = application.getSystemService(WINDOW_SERVICE) as WindowManager
        //设置好悬浮窗的参数
        wmParams = getMyParams()
        // 悬浮窗默认显示以左上角为起始坐标
        wmParams!!.gravity = Gravity.LEFT or Gravity.TOP
        //悬浮窗的开始位置，因为设置的是从左上角开始，所以屏幕左上角是x=0;y=0
        wmParams!!.x = winManager!!.defaultDisplay.width
        wmParams!!.y = 210
        //得到容器，通过这个inflater来获得悬浮窗控件
        inflater = LayoutInflater.from(applicationContext)
        // 获取浮动窗口视图所在布局
        mFloatingLayout = LayoutInflater.from(applicationContext).inflate(R.layout.remoteview, null)
        // 添加悬浮窗的视图
        winManager!!.addView(mFloatingLayout, wmParams)
    }

    fun getMyParams(): WindowManager.LayoutParams {
        wmParams = WindowManager.LayoutParams()
        //设置window type 下面变量2002是在屏幕区域显示，2003则可以显示在状态栏之上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        //设置可以显示在状态栏上
        wmParams.flags =
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        wmParams.format = PixelFormat.RGBA_8888

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        return wmParams
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        winManager!!.removeView(mFloatingLayout)
    }
}