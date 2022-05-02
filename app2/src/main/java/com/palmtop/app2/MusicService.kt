package com.palmtop.app2

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import kotlin.properties.Delegates

class MusicService : Service() {
    private val musicBinder = MusicBinder()
    private var player : MediaPlayer by Delegates.notNull()

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer()
        val assetFileFD = assets.openFd("bgmusic.mp3")
        player.setDataSource(assetFileFD.fileDescriptor, assetFileFD.startOffset, assetFileFD.length)
        player.isLooping = true
        player.prepare()
        player.start()
    }


    override fun onBind(intent: Intent): IBinder {
        return musicBinder
    }

    inner class MusicBinder : Binder() {

        fun play() {
            if (!player.isPlaying) {
                player.start()
            }
        }

        fun stop() {
            if (player.isPlaying) {
                player.pause()
            }
        }

        fun getDuration() = player.duration

        fun getCurrentPosition() = player.currentPosition

        fun isPlaying() = player.isPlaying

    }
}