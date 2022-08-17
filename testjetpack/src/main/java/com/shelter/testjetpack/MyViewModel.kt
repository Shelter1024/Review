package com.shelter.testjetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author: Shelter
 * Create time: 2022/8/15, 21:24.
 */
class MyViewModel : ViewModel() {
    var name : MutableLiveData<String>? = null
    get() {
        if (field == null) {
            field = MutableLiveData<String>()
        }
        return field
    }



}