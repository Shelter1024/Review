package com.palmtop.app2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.palmtop.app2.view.FishDrawable
import kotlinx.android.synthetic.main.activity_fish.*

class FishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fish)

        ivFish.setImageDrawable(FishDrawable())
    }
}