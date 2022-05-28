package com.hakretcode.stickynote.util

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HCLiveData<T> : MutableLiveData<T>() {
    private val handler = Handler(Looper.getMainLooper())
    override fun postValue(value: T) { handler.post { this.value = value } }
}