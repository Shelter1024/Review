package com.example.studykotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StudyKotlinActivity : AppCompatActivity() {
    var count: String
        get() = this.toString()
        set(value) {
            setDataFromValue(value)
        }

    fun setDataFromValue(value: String) {

    }

    //从 kotlin 1.1起，如果可以从get方法推断出类型，声明时可以省略类型
    val isEmpty
        get() = count == "StudyKotlinActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_kotlin)
    }


    var encryptData: String? = null
    fun encrypt(view: View) {
        val data = "StudyKotlinActivity"
        encryptData = EncryptUtil.aes(data, AppConstant.imageListKey, AppConstant.imageListIv)
        Log.d("Shelter", "StudyKotlinActivity encrypt encryptData:${encryptData}")
    }

    fun decrypt(view: View) {
        if (encryptData == null) return
        val decryptedData =
            EncryptUtil.decrypt(encryptData!!, AppConstant.imageListKey, AppConstant.imageListIv)
        Log.d("Shelter", "StudyKotlinActivity decrypt decryptedData:$decryptedData")
    }


}