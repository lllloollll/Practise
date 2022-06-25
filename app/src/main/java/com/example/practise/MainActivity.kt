package com.example.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.BaseAdapter
import android.widget.Chronometer
import android.widget.TextView
import com.example.practise.base.BaseActivity
import com.example.practise.notification.TestNotificationActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<TextView>(R.id.tv_test).setText("Hello World!")
        initChronometer()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_notification -> Intent(this, TestNotificationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    /**
     * 实现从10-0的倒计时
     */
    private fun initChronometer() {
        val chronometer = findViewById<Chronometer>(R.id.chronometer)
        chronometer.run {
            text = "10"             //初始化文本
            //SystemClock.elapsedRealtime()转化为00:00,加上10*1000ms,才是计时器开始计时的时间
            base = SystemClock.elapsedRealtime() + 1000 * 10
//            format = "%s"           //format只能添加字符，无法将00:00转换为其他的格式
            isCountDown = true      //是否倒计时
            start()                 //开始计时
            setOnChronometerTickListener {      //倒计时监听
                text = text.last().toString()   //截取最后一位
                if (text == "0")                //判断条件停止计时
                    stop()
            }
        }
    }
}