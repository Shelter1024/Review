package com.palmtop.app2

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.palmtop.app2.view.CountdownTimerView
import com.taoliu.news.modules.home.ui.widget.FloatWindow
import kotlinx.android.synthetic.main.activity_countdown_timer.*

class CountdownTimerActivity : AppCompatActivity() {
    private val OVERLAY_PERMISSION_REQ_CODE = 1000
    private lateinit var myWindowManager: WindowManager
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // 获取服务的操作对象
            val binder: FloatWindowService.MyBinder = service as FloatWindowService.MyBinder
            binder.service
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown_timer)
        requestPermissions(arrayOf(Manifest.permission.SYSTEM_ALERT_WINDOW), 0)
        val handler = Handler()
//        for (index in 0..5) {
//            handler.postDelayed(runnable, (index * 10000).toLong())
//        }
        btnStartService.setOnClickListener {
//            val intent = Intent(this@CountdownTimerActivity, FloatWindowService::class.java)
//            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
            checkPermission()
            val floatWindow = FloatWindow(this)
//            floatWindow.show()
        }


//        myWindowManager = applicationContext.getSystemService("")
    }

//    val runnable = Runnable { timerView.startAnim() }


    fun checkPermission() {
        if ( Build.VERSION.SDK_INT >= 23 ) {
            if (Settings.canDrawOverlays(this)) {
                Log.d("Shelter", "CountdownTimerActivity checkPermission 有权限")
                //有悬浮窗权限开启服务绑定 绑定权限
                addTimer()
            } else {
                Log.d("Shelter", "CountdownTimerActivity checkPermission 无权限，去申请权限")
                //没有悬浮窗权限m,去开启悬浮窗权限
                try {
//                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                    val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                    startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            addTimer()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            Log.d("Shelter", "CountdownTimerActivity onActivityResult 权限申请结果回调")
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(this)) {
                    Log.d("Shelter", "CountdownTimerActivity onActivityResult 申请失败")
                    Toast.makeText(this, "权限授予失败，无法开启悬浮窗", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("Shelter", "CountdownTimerActivity onActivityResult 申请成功")
                    Toast.makeText(this, "权限授予成功！", Toast.LENGTH_SHORT).show();
                    //有悬浮窗权限开启服务绑定 绑定权限
                    addTimer()
                }
            }
        }
    }


    private fun addTimer() {
        val timerView = CountdownTimerView(applicationContext)
//        var LAYOUT_FLAG: Int
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
//        } else {
//            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
//        }
//        val lp = WindowManager.LayoutParams(
//            wrapContent,
//            wrapContent,
//            LAYOUT_FLAG,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//            PixelFormat.TRANSPARENT
//        )
//        lp.gravity = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
//        windowManager.addView(timerView, lp)

//        val contentLayout = findViewById<ViewGroup>(android.R.id.content)
//        val lp = FrameLayout.LayoutParams(wrapContent, wrapContent)
//        lp.gravity = Gravity.RIGHT or Gravity.BOTTOM
//        lp.rightMargin = DensityUtil.dp2px(this, 20)
//        lp.bottomMargin = DensityUtil.dp2px(this, 200)
//        contentLayout.addView(timerView, lp)




//        timerView.addToWindow()
//        addToWindow()
    }

    private lateinit var layout : View

    fun addToWindow() {
//        val imageView = ImageView(this)
//        val layout = layoutInflater.inflate(R.layout.remoteviews, null)
        layout = LayoutInflater.from(applicationContext).inflate(R.layout.remoteview, null)
        layout.setOnTouchListener(TimerTouchListener())
//        layout.addView(imageView)
//        imageView.setImageResource(R.drawable.ic_red_envelope_collection_no)
        var LAYOUT_FLAG: Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE
        }
        val lp = WindowManager.LayoutParams()

        lp.type = LAYOUT_FLAG
//        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or
//                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR or
//                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.format = PixelFormat.RGBA_8888
        lp.x = 0
        lp.y = 0
        lp.gravity = Gravity.START or Gravity.TOP
//        lp.x = DensityUtil.getScreenWidth(context)
//        lp.y = DensityUtil.getScreenHeight(context)
//        layoutParams = lp
        val myWindowManager: WindowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        myWindowManager.addView(layout, lp)
    }


    //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchStartX = 0  //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchStartY = 0  //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchCurrentX = 0  //开始触控的坐标，移动时的坐标（相对于屏幕左上角的坐标）
    private var mTouchCurrentY = 0

    //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStartX = 0 //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStartY = 0  //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStopX = 0  //开始时的坐标和结束时的坐标（相对于自身控件的坐标）
    private var mStopY = 0

    //判断悬浮窗口是否移动，这里做个标记，防止移动后松手触发了点击事件
    private var isMove = false

//    inner class FloatingListener : View.OnTouchListener {
//        override fun onTouch(v: View, event: MotionEvent): Boolean {
//            val action = event.action
//            when (action) {
//                MotionEvent.ACTION_DOWN -> {
//                    isMove = false
//                    mTouchStartX = event.rawX.toInt()
//                    mTouchStartY = event.rawY.toInt()
//                    mStartX = event.x.toInt()
//                    mStartY = event.y.toInt()
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    mTouchCurrentX = event.rawX.toInt()
//                    mTouchCurrentY = event.rawY.toInt()
//                    wmParams.x += mTouchCurrentX - mTouchStartX
//                    wmParams.y += mTouchCurrentY - mTouchStartY
//                    windowManager.updateViewLayout(layout, wmParams)
//                    mTouchStartX = mTouchCurrentX
//                    mTouchStartY = mTouchCurrentY
//                }
//                MotionEvent.ACTION_UP -> {
//                    mStopX = event.x.toInt()
//                    mStopY = event.y.toInt()
//                    if (Math.abs(mStartX - mStopX) >= 1 || Math.abs(mStartY - mStopY) >= 1) {
//                        isMove = true
//                    }
//                }
//                else -> {}
//            }
//
//            //如果是移动事件不触发OnClick事件，防止移动的时候一放手形成点击事件
//            return isMove
//        }
//    }



}