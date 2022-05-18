package com.example.practise.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.practise.BuildConfig
import com.example.practise.R

abstract class BaseActivity : AppCompatActivity() {
     abstract fun onClick(v:View)

    companion object{
        val DEBUG= BuildConfig.DEBUG
    }
}