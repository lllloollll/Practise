package com.example.test_intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createAlarm("test alarm", 17, 56)
        startTimer("test timer",30)
    }

    fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        message.log()
        intent.resolveActivity(packageManager)?.let {
            "$message resolve".log()
            startActivity(intent)
        }
    }

    fun startTimer(message: String, seconds: Int) {
        val intent=Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE,message)
            putExtra(AlarmClock.EXTRA_LENGTH,seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI,true)
        }
        message.log()
        if (intent.resolveActivity(packageManager)!=null){
            "$message resolve".log()
            startActivity(intent)
        }
    }

    fun String.log() = this.apply {
        Log.d(TAG, this)
    }
}