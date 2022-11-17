package com.shelter.testroom

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class TestRoomActivity : AppCompatActivity() {
    private val REQUEST_EXTERNAL_STORAGE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_room)
        findViewById<View>(R.id.btn_insert).setOnClickListener(onClickListener)
        findViewById<View>(R.id.btn_delete).setOnClickListener(onClickListener)
        findViewById<View>(R.id.btn_update).setOnClickListener(onClickListener)
        findViewById<View>(R.id.btn_query).setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener {
        val permissionList = ArrayList<String>()
        val readPermissionGranted =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (readPermissionGranted != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val writePermissionGranted =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (writePermissionGranted != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (permissionList.isNotEmpty()) {
            Log.d("Shelter", "TestRoomActivity 申请权限")
            ActivityCompat.requestPermissions(
                this, permissionList.toTypedArray(), REQUEST_EXTERNAL_STORAGE
            )
            return@OnClickListener
        } else {
            Log.d("Shelter", "TestRoomActivity 有权限")
        }
        when (it.id) {
            R.id.btn_insert -> {
                val userEvent = UserEvent()
                userEvent.appLaunchId = "123456"
                val result = DBHelper.getInstance(this).insertUserEvent(userEvent)
                Log.d("Shelter", "TestRoomActivity result=${result}")
            }

//            R.id.btn_delete -> {
//                val userEvent = UserEvent()
//                userEvent.id = 5
//                userEvent.bookName = "《活着》"
//                userEvent.author = "余华"
////                DBHelper.getInstance(this).deleteBook(book)
//                DBHelper.getInstance(this).deleteAll()
//            }
//
//            R.id.btn_update -> {
//                val userEvent = UserEvent()
//                userEvent.id = 7
//                userEvent.bookName = "《活着》"
//                userEvent.author = "余华2"
//                DBHelper.getInstance(this).updateUserEvent(userEvent)
//            }
//
//            R.id.btn_query -> {
//                val book = DBHelper.getInstance(this).queryUserEvent(1)
//                Log.d("Shelter", "TestRoomActivity book:$book")
//            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            Log.d(
                "Shelter",
                "TestRoomActivity onRequestPermissionsResult ${grantResults.contentToString()}"
            )
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "权限申请失败", Toast.LENGTH_LONG).show()
            }
        }
    }


}