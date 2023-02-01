package com.example.quotesapp

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    var count = 0
    fun increment(){
        count++
    }
    fun decrement(){
        count--
    }
}