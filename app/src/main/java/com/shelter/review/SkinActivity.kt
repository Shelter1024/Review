package com.shelter.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.shelter.skinlib.SkinManager
import kotlinx.android.synthetic.main.activity_skin.*
import org.jetbrains.anko.toast

class SkinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin)
        tvTitle.setOnClickListener{
            toast("点击了按钮")
            val apkPath = "data/user/0/com.shelter.review/cache/skinapp-debug.apk"
            SkinManager.getInstance().loadSkin(apkPath)
        }
    }
}