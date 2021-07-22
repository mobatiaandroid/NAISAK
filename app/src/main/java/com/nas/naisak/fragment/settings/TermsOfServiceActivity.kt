package com.nas.naisak.fragment.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.fragment.settings.model.TermsOfServiceModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsOfServiceActivity : AppCompatActivity(){
    lateinit var mContext: Context
    var message:String=""
    var url:String=""
    var date:String=""
    private lateinit var relativeHeader: RelativeLayout
    private lateinit var backRelative: RelativeLayout
    private lateinit var logoClickImgView: ImageView
    private lateinit var btn_left: ImageView
    private lateinit var heading: TextView
    private lateinit var webView: WebView
    var myFormatCalende:String="yyyy-MM-dd HH:mm:ss"
    private lateinit var progressDialog: RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_of_services)
        mContext=this
        initUI()
        callMessageDetailAPI()
        getSettings()

    }
    fun initUI() {
        relativeHeader = findViewById(R.id.relativeHeader)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        backRelative = findViewById(R.id.backRelative)
        logoClickImgView = findViewById(R.id.logoClickImgView)
        progressDialog = findViewById(R.id.progressDialog)
        webView = findViewById(R.id.webView)
        heading.setText("Terms of Services")
        btn_left.setOnClickListener(View.OnClickListener {
            finish()
        })
        backRelative.setOnClickListener(View.OnClickListener {
            finish()
        })
        logoClickImgView.setOnClickListener(View.OnClickListener {
            val intent = Intent(mContext, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        })
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
    }
    fun callMessageDetailAPI()
    {
        val token = PreferenceManager.getUserCode(mContext)
        val call: Call<TermsOfServiceModel> = ApiClient.getClient.termsofservice()
        call.enqueue(object : Callback<TermsOfServiceModel> {
            override fun onFailure(call: Call<TermsOfServiceModel>, t: Throwable) {
                progressDialog.visibility = View.GONE
                Log.e("Error", t.localizedMessage)
            }
            override fun onResponse(call: Call<TermsOfServiceModel>, response: Response<TermsOfServiceModel>) {
                progressDialog.visibility = View.GONE
                if (response.body()!!.status==100)
                {
                    var termsTitle=response.body()!!.data.lists.get(0).title
                    var termsDescription=response.body()!!.data.lists.get(0).description

                    var pushNotificationDetail="<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<head>\n" +
                            "<style>\n" +
                            "\n" +
                            "@font-face {\n" +
                            "font-family: SourceSansPro-Semibold;"+
                            "src: url(SourceSansPro-Semibold.ttf);"+

                            "font-family: SourceSansPro-Regular;"+
                            "src: url(SourceSansPro-Regular.ttf);"+
                            "}"+
                            ".title {"+
                            "font-family: SourceSansPro-Regular;"+
                            "font-size:16px;"+
                            "text-align:left;"+
                            "color:	#46C1D0;"+
                            "text-align: ####TEXT_ALIGN####;"+
                            "}"+

                            ".description {"+
                            "font-family: SourceSansPro-Light;"+
                            "text-align:justify;"+
                            "font-size:14px;"+
                            "color: #000000;"+
                            "text-align: ####TEXT_ALIGN####;"+
                            "}"+
                            "</style>\n"+"</head>"+
                            "<body>"+
                            "<p class='title'>"+termsTitle+"</p>"+"<p class='description'>"+termsDescription+"</p>"+
                            "</body>\n</html>";
                    var htmlData=pushNotificationDetail
                    Log.e("HTML DATA",htmlData)
                    //  webView.loadData(htmlData,"text/html; charset=utf-8","utf-8")
                    webView.loadDataWithBaseURL("file:///android_asset/fonts/",htmlData,"text/html; charset=utf-8", "utf-8", "about:blank")

                }


            }

        })
    }

    fun getSettings()
    {
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.setAppCacheEnabled(false)
        webView.settings.javaScriptCanOpenWindowsAutomatically = true
        webView.settings.domStorageEnabled = true
        webView.settings.databaseEnabled = true
        webView.settings.defaultTextEncodingName = "utf-8"
        webView.settings.loadsImagesAutomatically = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.allowFileAccess = true
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null)


        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                progressDialog.visibility = View.VISIBLE
                println("testing2")
                if (newProgress == 100)
                {
                    println("testing1")
                    progressDialog.visibility = View.GONE

                }
            }
        }
    }
}
