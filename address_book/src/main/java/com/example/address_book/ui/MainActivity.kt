package com.example.address_book.ui

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.address_book.R
import com.example.address_book.adapter.AdressBookAdapter
import com.example.address_book.base.BaseActivity
import com.example.address_book.model.AdressBookModel

class MainActivity : BaseActivity() {
    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    private val rv: RecyclerView
        get() = findViewById(R.id.main_rv)

    private val adapter = AdressBookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv.run {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = this@MainActivity.adapter.apply {
                clickItem ={
                    it.name.toast()
                    showAdressDlg(it)
                }
            }
        }
        getData()
    }

    private fun getData() {
        val data = arrayListOf<AdressBookModel>()
        for (index in 0..50) {
            AdressBookModel("maomao$index", "10086118086")
                .let { data.add(it) }
        }
        adapter.setData(data)
    }

    fun String.toast() = this.apply {
        Toast.makeText(this@MainActivity, this, Toast.LENGTH_SHORT).show()
    }

    fun String.log() = this.apply {
        Log.d(TAG, this)
    }

    private val dlgAdress: Dialog
        get() {
            return Dialog(this).apply {
                setContentView(R.layout.dlg_adress)
                window?.setBackgroundDrawableResource(R.color.tranparent)
                window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
                val lp = findViewById<ConstraintLayout>(R.id.dlg_container).layoutParams
                        as FrameLayout.LayoutParams
                lp.marginStart = 56
                lp.marginEnd = 56
                name = findViewById(R.id.dlg_tv_name)
                phoneNumber = findViewById(R.id.dlg_tv_phone_number)
                btnCall = findViewById(R.id.dlg_btn_call)
                btnSendMsg = findViewById(R.id.dlg_btn_send_msg)
            }
        }
    private lateinit var name: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var btnCall: Button
    private lateinit var btnSendMsg: Button

    private fun showAdressDlg(model: AdressBookModel) {
        dlgAdress.run {
            name.text = model.name
            phoneNumber.text = model.phoneNumber
            btnCall.setOnClickListener { }
            show()
        }
    }

    private interface DlgBtnListner {
        fun callBtnListener(model: AdressBookModel)
        fun sendMsgBtnListener(model: AdressBookModel)
    }
}