package com.example.screen_save

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.service.dreams.DreamService
import com.example.base.utils.LogUtils
import com.example.screen_save.databinding.ActivityMainBinding

class MyDreamService : DreamService() {
    companion object {
        private val TAG = MyDreamService::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        LogUtils.d(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d(TAG, "onDestroy")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        LogUtils.d(TAG, "onAttachedToWindow")
        isInteractive = false
        isFullscreen = true
        setContentView(R.layout.activity_main)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        LogUtils.d(TAG, "onDetachedFromWindow")
    }

    override fun onDreamingStarted() {
        super.onDreamingStarted()
        LogUtils.d(TAG, "onDreamingStarted")
    }

    override fun onDreamingStopped() {
        super.onDreamingStopped()
        LogUtils.d(TAG, "onDreamingStopped")
    }
}