package com.palmtop.app2

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.palmtop.app2.view.FishDrawable
import kotlinx.android.synthetic.main.activity_fish.*
import kotlin.properties.Delegates

class FishActivity : AppCompatActivity() {
    private var soundPool: SoundPool by Delegates.notNull()
    private var soundId: Int = 0
    private var musicBinder: MusicService.MusicBinder? = null

    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("Shelter", "FishActivity onServiceConnected: ")
            musicBinder = service as MusicService.MusicBinder?
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("Shelter", "FishActivity onServiceDisconnected:  name: $name")
        }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fish)

//        val fishDrawable = FishDrawable()
//        ivFish.setImageDrawable(fishDrawable)

        initSoundPool()

        val intent = Intent(this, MusicService::class.java)
        startService(intent)

        bindService(intent, conn, Service.BIND_AUTO_CREATE)

        btnPlay.setOnClickListener {
            if (musicBinder?.isPlaying() == true) {
                musicBinder?.stop()
            } else {
                musicBinder?.play()
            }
            updateBtnText()
        }
    }

    private fun updateBtnText() {
        btnPlay.text = if (musicBinder?.isPlaying() == true) {
            "暂停音乐"
        } else {
            "播放音乐"
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initSoundPool() {
        soundPool = SoundPool.Builder().build()
        soundId = soundPool.load(this, R.raw.water, 1)
    }

    public fun playClickSound() {
        soundPool.play(soundId, 0.5f, 0.5f, 1, 0, 1f)
    }


    override fun onResume() {
        super.onResume()
        musicBinder?.play()
    }

    override fun onStop() {
        super.onStop()
        musicBinder?.stop()
    }

}