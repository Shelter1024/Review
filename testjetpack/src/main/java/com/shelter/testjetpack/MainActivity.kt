package com.shelter.testjetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.shelter.testjetpack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.book = Book("《演员的自我修养》", "Shelter")
        binding.eventHandler = EventHandler(this)
        binding.model = TwoWayBindingViewModel()
        binding.model?.setAuthor("修改过后：谢添")
    }

}