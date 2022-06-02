package com.example.address_book.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.address_book.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}