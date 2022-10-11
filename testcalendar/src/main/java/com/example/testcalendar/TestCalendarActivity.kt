package com.example.testcalendar

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class TestCalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_canlendar)

        findViewById<View>(R.id.btn_add_calendar_event).setOnClickListener {
            val array = checkCalendarPermission()
            if (array.isEmpty()) {
                Log.d("Shelter", "TestCalendarActivity onCreate 有权限，添加日历")
                addCalendarEvent()
            } else {
                Log.d("Shelter", "TestCalendarActivity onCreate 无权限，去申请")
                ActivityCompat.requestPermissions(this, array, 0)
            }
        }

        val computingView: ComputingView = findViewById(R.id.computing_view)
        computingView.startAnim(6890)
    }

    private fun addCalendarEvent() {
        val title = "我是标题"
        val desc = "我是描述"
        val reminderTime = System.currentTimeMillis() + 30000
        val previousDay = 1

        CalendarReminderUtil.addCalendarEvent(this)
    }

    private fun checkCalendarPermission(): Array<String> {
        val permissionList = ArrayList<String>()
        val readPermissionGranted =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
        if (readPermissionGranted != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CALENDAR)
        }

        val writePermissionGranted =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
        if (writePermissionGranted != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CALENDAR)
        }

        return permissionList.toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(
            "Shelter",
            "TestCalendarActivity onRequestPermissionsResult ${grantResults.contentToString()} \n ${permissions.contentToString()}"
        )
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show()
                addCalendarEvent()
            }
        }
    }


}