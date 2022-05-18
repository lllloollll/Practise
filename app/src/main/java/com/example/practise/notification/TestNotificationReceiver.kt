package com.example.practise.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.practise.utils.LogUtils

class TestNotificationReceiver : BroadcastReceiver() {
    companion object {
        val ACTION_TEST_01 = "action_test_01"
        val EXTRA_TEST_01 = "extra_test_01"
    }

    fun log(content: String) {
        LogUtils.d(TestNotificationReceiver::class.java.simpleName, content)
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        when (intent.action) {
            ACTION_TEST_01 -> {
                log("extra:" + intent.getIntExtra(EXTRA_TEST_01, 666))
            }
        }
    }
}