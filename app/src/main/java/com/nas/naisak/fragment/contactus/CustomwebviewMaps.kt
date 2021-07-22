package com.nas.naisak.fragment.contactus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity

class CustomwebviewMaps : AppCompatActivity() {
    lateinit var back: ImageView
    lateinit var titleTextView: TextView
    lateinit var context: Context
    lateinit var webview: WebView
    lateinit var progressbar: ProgressBar
    var urltoshow: String = ""
    var title:String = ""
    lateinit var logoclick: ImageView


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customwebviewmaps)
        context = this

        urltoshow = intent.getStringExtra("webview_url")
        title = intent.getStringExtra("title")

        back = findViewById(R.id.back)
        titleTextView=findViewById(R.id.titleTextView)
        webview = findViewById(R.id.webviewmaps)
        logoclick = findViewById(R.id.logoclick)
        webview.settings.javaScriptEnabled = true
        webview.settings.setAppCacheEnabled(true)
        webview.settings.javaScriptCanOpenWindowsAutomatically = true
        webview.settings.loadsImagesAutomatically = true
        webview.setBackgroundColor(Color.TRANSPARENT)
        webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)
        progressbar = findViewById(R.id.progress)
        webview.webViewClient = MyWebViewClient(this)

        titleTextView.text = title


        if (urltoshow.contains("http")) {
            urltoshow = urltoshow.replace("http", "https")
        }

        webview.loadUrl(urltoshow)

        webview.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressbar.progress = newProgress
                if (newProgress == 100) {
                    progressbar.visibility = View.GONE
                    back.visibility = View.VISIBLE

                }
            }
        }
        logoclick.setOnClickListener {
            val mIntent = Intent(context, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(mIntent)
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