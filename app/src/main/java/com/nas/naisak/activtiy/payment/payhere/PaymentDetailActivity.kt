package com.nas.naisak.activtiy.payment.payhere

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintJob
import android.print.PrintManager
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.nas.naisak.R
import com.nas.naisak.activity.payment.payhere.model.PaymentDetailApiModel
import com.nas.naisak.activity.payment.payhere.model.PaymentDetailResponseModel
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class PaymentDetailActivity  : AppCompatActivity(){
    lateinit var mContext: Context
    lateinit var relativeHeader: RelativeLayout
    lateinit var backRelative: RelativeLayout
    lateinit var logoClickImgView: ImageView
    lateinit var btn_left: ImageView
    lateinit var heading: TextView

    lateinit var payTotalButton: Button
    lateinit var totalLinear: LinearLayout
    lateinit var mainLinear: LinearLayout
    lateinit var printLinear: LinearLayout
    lateinit var printLinearClick: LinearLayout
    lateinit var paidImg: ImageView
    lateinit var paymentWeb: WebView
    lateinit var mProgressRelLayout: RelativeLayout
    lateinit var printJob: PrintJob
    lateinit var description: TextView
    lateinit var totalAmount: TextView

    var id: String = ""
    var title: String = ""
    var fullHtml: String = ""
    var payment_type_invoice: String = ""
    var current: String = ""
    var formated_amt: String = ""
    var descriptionTxt: String = ""
    var formated_amount: String = ""
    var student_name: String = ""
    var payment_date: String = ""
    var invoice_note: String = ""
    var isams_no: String = ""
    var trn_no: String = ""
    var payment_type_print: String = ""
    var invoice_description: String = ""
    var invoice_no: String = ""
    var payment_type:Int=0
    var is_paid:Int=0
    lateinit var anim: RotateAnimation
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_detail)
        mContext=this
        initUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            callPayementDetail()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }

    }
    fun initUI() {
        id = intent.getStringExtra("id")
        title = intent.getStringExtra("title")
        relativeHeader = findViewById(R.id.relativeHeader)
        backRelative = findViewById(R.id.backRelative)
        heading = findViewById(R.id.heading)
        btn_left = findViewById(R.id.btn_left)
        logoClickImgView = findViewById(R.id.logoClickImgView)

        payTotalButton = findViewById(R.id.payTotalButton)
        totalLinear = findViewById(R.id.totalLinear)
        paidImg = findViewById(R.id.paidImg)
        mainLinear = findViewById(R.id.mainLinear)
        printLinear = findViewById(R.id.printLinear)
        printLinearClick = findViewById(R.id.printLinearClick)
        paymentWeb = findViewById(R.id.paymentWeb)
        mProgressRelLayout = findViewById(R.id.progressDialog)
        description = findViewById(R.id.description)
        totalAmount = findViewById(R.id.totalAmount)
        mProgressRelLayout.visibility= View.GONE

        heading.setText("Absence")


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

        printLinear.setOnClickListener(View.OnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                paymentWeb.loadUrl("about:blank")
                setWebViewSettingsPrint()
                loadWebViewWithDataPrint()
                createWebPrintJob(paymentWeb)
            } else {
                Toast.makeText(
                    mContext,
                    "Print is not supported below Android KITKAT Version",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })



    }
    private fun createWebPrintJob(webView: WebView) {
        mProgressRelLayout.clearAnimation()
        mProgressRelLayout.visibility = View.GONE
        paymentWeb.visibility = View.GONE
        val printManager = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            this.getSystemService(PRINT_SERVICE) as PrintManager
        } else {
            TODO("VERSION.SDK_INT < KITKAT")
        }
        val printAdapter = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.createPrintDocumentAdapter()
        } else {
            TODO("VERSION.SDK_INT < KITKAT")
        }
        val jobName = getString(R.string.app_name) + "_Pay" + "NASDUBAI"
        val builder = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            PrintAttributes.Builder()
        } else {
            TODO("VERSION.SDK_INT < KITKAT")
        }
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4)
        if (printManager != null) {
            printJob = printManager.print(jobName, printAdapter, builder.build())
        }
        if (printJob.isCompleted()) {
        } else if (printJob.isFailed()) {
            Toast.makeText(applicationContext, "Print failed", Toast.LENGTH_SHORT).show()
        }
    }
    fun loadWebViewWithDataPrint()
    {
        var sb = StringBuilder()
        var eachLine = ""
        try {
           val br = BufferedReader(InputStreamReader(assets.open("receipfee.html")))
            sb = StringBuilder()
            eachLine = br.readLine()
            while (eachLine != null) {
                sb.append(eachLine)
                sb.append("\n")
                eachLine = br.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }



        fullHtml = sb.toString()

        if (fullHtml.length > 0) {
            fullHtml = fullHtml.replace("###amount###", current)
            fullHtml = fullHtml.replace("###ParentName###", student_name)
            fullHtml = fullHtml.replace("###Date###", payment_date)
            fullHtml = fullHtml.replace("###paidBy###", invoice_note)
            fullHtml = fullHtml.replace("###billing_code###", isams_no)
            fullHtml = fullHtml.replace("###title###", invoice_description)
            fullHtml = fullHtml.replace("###trn_no###", trn_no)
            fullHtml = fullHtml.replace("###order_Id###", invoice_no)
            fullHtml = fullHtml.replace("###payment_type###", payment_type_print)
            fullHtml = fullHtml.replace("###percentageAmount###", "5")
            fullHtml = fullHtml.replace("###full-amount###", formated_amount)
            fullHtml = fullHtml.replace("###percent###", "($5%)")
            paymentWeb.loadDataWithBaseURL(
                "file:///android_asset/images/",
                fullHtml,
                "text/html; charset=utf-8",
                "utf-8",
                "about:blank"
            )
        }

    }
    private fun setWebViewSettingsPrint() {
        mProgressRelLayout.setVisibility(View.VISIBLE)
        anim = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        anim.setInterpolator(mContext, android.R.interpolator.linear)
        anim.setRepeatCount(Animation.INFINITE)
        anim.setDuration(1000)
        mProgressRelLayout.setAnimation(anim)
        mProgressRelLayout.startAnimation(anim)
        paymentWeb.settings.javaScriptEnabled = true
        paymentWeb.clearCache(true)
        paymentWeb.settings.domStorageEnabled = true
        paymentWeb.settings.javaScriptCanOpenWindowsAutomatically = true
        paymentWeb.settings.setSupportMultipleWindows(true)
        paymentWeb.webViewClient = PaymentDetailActivity.MyPrintWebViewClient()
//        paymentWeb.setWebChromeClient(new MyWebChromeClient());
    }

    fun callPayementDetail()
    {
        val token = PreferenceManager.getUserCode(mContext)
        val paymentID = PaymentDetailApiModel(id)
        val call: Call<PaymentDetailResponseModel> =
            ApiClient.getClient.paymentdetail(paymentID, "Bearer " + token)
        call.enqueue(object : Callback<PaymentDetailResponseModel> {
            override fun onFailure(call: Call<PaymentDetailResponseModel>, t: Throwable) {
                Log.e("Error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<PaymentDetailResponseModel>,
                response: Response<PaymentDetailResponseModel>
            ) {
                if (response.body()!!.status == 100) {
                    payment_type = response.body()!!.data.payment_type
                    if (payment_type == 1) {
                        payment_type_invoice = "Online"
                    } else if (payment_type == 2) {
                        payment_type_invoice = "Cash"
                    } else if (payment_type == 3) {
                        payment_type_invoice = "Cheque"
                    } else if (payment_type == 4) {
                        payment_type_invoice = "Bank Transfer"
                    } else if (payment_type == 5) {
                        payment_type_invoice = "Online"
                    } else {
                        payment_type_invoice = "Online"
                    }

                    is_paid = response.body()!!.data.is_paid
                    formated_amount = response.body()!!.data.formated_amount
                    current = response.body()!!.data.amount
                    student_name = response.body()!!.data.student_name
                    payment_date = response.body()!!.data.payment_date
                    invoice_note = response.body()!!.data.invoice_note
                    isams_no = response.body()!!.data.isams_no
                    invoice_description = response.body()!!.data.invoice_description
                    payment_type_print = payment_type_invoice
                    trn_no = response.body()!!.data.trn_no
                    invoice_no = response.body()!!.data.invoice_no
                    if (is_paid == 0) {
                        payTotalButton.visibility = View.VISIBLE
                        totalLinear.visibility = View.VISIBLE
                        paidImg.visibility = View.GONE
                        mainLinear.visibility = View.VISIBLE
                        printLinear.visibility = View.GONE
                    } else {
                        payTotalButton.visibility = View.GONE
                        totalLinear.visibility = View.VISIBLE
                        paidImg.visibility = View.VISIBLE
                        mainLinear.visibility = View.VISIBLE
                        printLinear.visibility = View.VISIBLE
                    }
                    descriptionTxt = response.body()!!.data.invoice_description
                    description.setText(descriptionTxt)

                    formated_amt = response.body()!!.data.formated_amount
                    totalAmount.setText(formated_amt)


                } else {

                }
            }

        })
    }


    private class MyPrintWebViewClient : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            //Calling a javascript function in html page

//            view.loadUrl(url);
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            super.onPageStarted(view, url, favicon)

            //   Log.d("WebView", "print webpage loading.." + url);
        }
    }


}