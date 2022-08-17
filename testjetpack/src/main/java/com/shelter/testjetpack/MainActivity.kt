package com.shelter.testjetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(MyObserver())
        Log.d("Shelter", "MainActivity onCreate: ")
        val mutableLiveData1 = MutableLiveData<String>()
        val mutableLiveData2 = MutableLiveData<String>()
        val mediatorLiveData = MediatorLiveData<Boolean>()

//        mutableLiveData1.observe(this) {
//            Log.d("Shelter", "mutableLiveData1 onChanged: $it")
//        }
//        mutableLiveData1.postValue("hello, Shelter")
//        val mapLiveData = Transformations.map(mutableLiveData1) {
//            "$it---test map"
//        }
//        mapLiveData.observe(this) {
//            Log.d("Shelter", "mapLiveData onChanged: $it")
//        }
//        mutableLiveData1.postValue("哈哈哈")

        val switchMapLiveData = Transformations.switchMap(mediatorLiveData) {
            if (it) {
                mutableLiveData1
            } else {
                mutableLiveData2
            }
        }
        switchMapLiveData.observe(this) {
            Log.d("Shelter", "switchMapLiveData onChanged: $it")
        }
        mediatorLiveData.postValue(false)
        mutableLiveData1.postValue("11111")
        mutableLiveData2.postValue("22222")

        val myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        myViewModel.name?.observe(this) {

        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}