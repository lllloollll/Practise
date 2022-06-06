package com.example.test_intent

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.*
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        createAlarm("test alarm", 17, 56)
//        startTimer("test timer",30)
//        showAlarms()
//        addEvent("小周", "guang zhou")
//        selectImg()
//        playSearchArtist("青花瓷")
//        dialPhoneNumber("15815891264")
//        searchWeb("毛毛虫")
//        openBluetoothSetting()
        composeMmsMessage("hello world!")
    }

    fun createAlarm(message: String, hour: Int, minutes: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_HOUR, hour)
            putExtra(AlarmClock.EXTRA_MINUTES, minutes)
        }
        message.log()
//        intent.resolveActivity(packageManager)?.let {
//            "$message resolve".log()
        startActivity(intent)
//        }
    }

    fun startTimer(message: String, seconds: Int) {
        val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, message)
            putExtra(AlarmClock.EXTRA_LENGTH, seconds)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
        message.log()
//        if (intent.resolveActivity(packageManager)!=null){
//            "$message resolve".log()
        startActivity(intent)
//        }
    }

    fun showAlarms() {
        val intent = Intent(AlarmClock.ACTION_SHOW_ALARMS)
        startActivity(intent)
    }

    fun addEvent(title: String, location: String) {
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.Events.EVENT_LOCATION, location)
            putExtra(CalendarContract.Events.ALL_DAY, true)
        }
        startActivity(intent)
    }

    fun selectImg() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, 1001)
    }

    fun playSearchArtist(artist: String) {
        val intent = Intent(MediaStore.INTENT_ACTION_MEDIA_PLAY_FROM_SEARCH).apply {
            putExtra(MediaStore.EXTRA_MEDIA_FOCUS, MediaStore.Audio.Artists.ENTRY_CONTENT_TYPE)
            putExtra(MediaStore.EXTRA_MEDIA_ARTIST, artist)
            putExtra(SearchManager.QUERY, artist)
        }
        startActivity(intent)
    }

    fun dialPhoneNumber(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    fun searchWeb(content: String) {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, content)
        }
        startActivity(intent)
    }

    fun openBluetoothSetting() {
        val intent = Intent(Settings.ACTION_APN_SETTINGS)
        startActivity(intent)
    }

    fun composeMmsMessage(message:String){
        val intent=Intent(Intent.ACTION_SEND).apply {
            data=Uri.parse("smsto:")
            putExtra("sms_body",message)
        }
        startActivity(intent)
    }

    fun String.log() = this.apply {
        Log.d(TAG, this)
    }
}