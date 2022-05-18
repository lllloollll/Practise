package com.example.practise

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.practise.base.BaseActivity
import com.example.practise.notification.TestNotificationActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        findViewById<TextView>(R.id.tv_test).setText("Hello World!")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_notification -> Intent(this, TestNotificationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}