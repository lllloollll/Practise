package com.example.practise.testjs

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.TextView
import com.example.practise.R
import com.example.practise.base.BaseActivity
import com.example.practise.utils.LogUtils
import com.example.practise.utils.VUiKit
import java.io.ByteArrayOutputStream
import java.net.CookieStore

class TestJsActivity : BaseActivity() {
    private val webView: WebView
        get() = findViewById(R.id.webview)
    private val tvUrl: TextView
        get() = findViewById(R.id.tv_url)

    private val TAG = TestJsActivity::class.java.simpleName

    private fun log(content: String) {
        LogUtils.d(TAG, content)
    }

    override fun onClick(v: View) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_js)
        initView()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanWebview()
    }

    private fun initView() {
        val url1 = "https://www.baidu.com"
        val url2 = "javascript:jclqBonusRange()"
        val url3 = "file:///android_asset/bonus_helper.html"
        tvUrl.text = url3
        webView.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            textZoom = 100
            javaScriptCanOpenWindowsAutomatically = true
            defaultTextEncodingName = "utf - 8"
            allowFileAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
        }
        webView.addJavascriptInterface(JsToJava(), "stub")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                log("on page start")

                VUiKit.postDelayed(2000) {
                    webView.loadUrl(url2)
                    tvUrl.text = url2
                }
            }


            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                log("on received error:$error")
            }
        }
        webView.loadUrl(url3)
    }


    private fun cleanWebview() {
        CookieSyncManager.createInstance(this)
        CookieManager.getInstance().removeAllCookie()
        CookieSyncManager.getInstance().sync()

        webView.run {
            webChromeClient = null
            cleanWebview()
            settings.javaScriptEnabled = false
            loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            clearHistory()
            clearCache(true)
            supportFragmentManager
            removeAllViews()
            destroy()
        }


    }

    private inner class JsToJava {
        @JavascriptInterface
        fun jsCallbackMethod(result: String) {
            log("result:$result")
        }
    }


}