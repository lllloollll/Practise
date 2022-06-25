package com.example.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    protected lateinit var viewBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //android6.0以后可以对状态栏文字颜色和图标进行修改
            setSystemBarDark()
        }
        window.statusBarColor = getStatusBarColor()
        window.navigationBarColor=getStatusBarColor()
        viewBinding = onCreateViewBinding(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
    }

    protected abstract fun onCreateViewBinding(layoutInflater: LayoutInflater): T

    protected abstract fun initViews()

    protected open fun getStatusBarColor(): Int = resources.getColor(R.color.bg)

    @SuppressLint("WrongConstant")
    protected fun setSystemBarLight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsControllerCompat = ViewCompat.getWindowInsetsController(
                window.decorView
            )
            if (insetsControllerCompat != null) {
                insetsControllerCompat.isAppearanceLightStatusBars = false
                window.setDecorFitsSystemWindows(false)
                window.statusBarColor = Color.TRANSPARENT
                insetsControllerCompat.show(WindowInsets.Type.statusBars())
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

    }

    @SuppressLint("WrongConstant")
    protected fun setSystemBarDark() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val insetsControllerCompat = ViewCompat.getWindowInsetsController(
                window.decorView
            )
            if (insetsControllerCompat != null) {
                insetsControllerCompat.isAppearanceLightStatusBars = true
                window.setDecorFitsSystemWindows(false)
                window.statusBarColor = Color.TRANSPARENT
                insetsControllerCompat.show(WindowInsets.Type.statusBars())
            }
        } else {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

}