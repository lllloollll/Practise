package com.example.screen_save

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.dreams.DreamService
import android.view.LayoutInflater
import com.example.base.BaseActivity
import com.example.base.utils.LogUtils
import com.example.screen_save.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreateViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun getStatusBarColor(): Int = resources.getColor(R.color.black)

    override fun initViews() {
        setSystemBarLight()
        viewBinding.btnStart.setOnClickListener {
            startScreenHolder()
        }
    }

    private fun startScreenHolder() {
//        val intent=Intent(Intent.ACTION_DREAMING_STARTED)
//        intent.
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        startService(intent)



    }
}