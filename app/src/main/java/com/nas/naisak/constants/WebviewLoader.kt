package com.nas.naisak.constants

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.nas.naisak.R

class WebviewLoader : AppCompatActivity() {
    lateinit var back: ImageView

    //lateinit var downloadpdf: ImageView
    lateinit var context: Context
    lateinit var webview: WebView
    lateinit var progressbar: ProgressBar
    var urltoshow: String = ""


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_loader)
        context = this

        urltoshow = intent.getStringExtra("webview_url")
        back = findViewById(R.id.back)
        // downloadpdf = findViewById(R.id.downloadpdf)
        webview = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
        webview.settings.setAppCacheEnabled(true)
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.settings.loadsImagesAutomatically = true
        webview.setBackgroundColor(Color.TRANSPARENT)
        webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        progressbar = findViewById(R.id.progress)
        webview.webViewClient = MyWebViewClient(this)


//        if (urltoshow.contains("http")) {
//            urltoshow = urltoshow.replace("http", "https")
//        }

        webview.loadUrl(urltoshow)
        Log.e("LOADINGURL==>",urltoshow)

        webview.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressbar.progress = newProgress
                if (newProgress == 100) {
                    progressbar.visibility = View.GONE
                    back.visibility = View.VISIBLE

                }
            }
        }

        back.setOnClickListener {
            finish()
        }
    }

    class MyWebViewClient internal constructor(private val activity: Activity) : WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)

            return true
        }
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
        }

    }

}