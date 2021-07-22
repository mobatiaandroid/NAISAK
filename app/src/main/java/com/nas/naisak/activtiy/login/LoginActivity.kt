package com.nas.naisak.activtiy.login

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.activtiy.login.model.LoginResponse
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var emailHelpImg:Button
    lateinit var signupImg:Button
    lateinit var forgotPasswordImg:Button
    lateinit var guestButton:Button
    lateinit var loginImg:Button
    lateinit var emailTxt: EditText
    lateinit var progressDialog: ProgressBar
    lateinit var passwordEditTxt: EditText
    var isClicked:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mContext=this
        initUI()
    }
    @SuppressLint("ClickableViewAccessibility")
    fun initUI()
    {
        emailHelpImg=findViewById(R.id.helpButton)
        signupImg=findViewById(R.id.signUpButton)
        forgotPasswordImg=findViewById(R.id.forgotPasswordButton)
        loginImg=findViewById(R.id.loginBtn)
        emailTxt = findViewById(R.id.userEditText)
        passwordEditTxt = findViewById(R.id.passwordEditText)
        guestButton = findViewById(R.id.guestButton)
        progressDialog = findViewById(R.id.progressDialog)
//        emailTxt.setOnTouchListener(this)
//        passwordEditTxt.setOnTouchListener(this)
        signupImg.setOnClickListener(View.OnClickListener {

           if (isClicked)
           {

           }
            else
           {
               val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
               imm?.hideSoftInputFromWindow(emailTxt.windowToken, 0)
               val immq = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
               immq?.hideSoftInputFromWindow(passwordEditTxt.windowToken, 0)
               isClicked=true
               showSignupDialog(mContext)
           }

        })
        forgotPasswordImg.setOnClickListener(View.OnClickListener {

           if (isClicked)
           {

           }
            else
           {
               isClicked=true
               val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
               imm?.hideSoftInputFromWindow(emailTxt.windowToken, 0)
               val immq = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
               immq?.hideSoftInputFromWindow(passwordEditTxt.windowToken, 0)
               showForgotPassword(mContext)
           }

        })

        guestButton.setOnClickListener(View.OnClickListener {

            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        })
        loginImg.setOnClickListener(View.OnClickListener {

           if (isClicked)
           {

           }
            else
           {
               isClicked=true
               val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
               imm?.hideSoftInputFromWindow(emailTxt.windowToken, 0)
               val immq = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
               immq?.hideSoftInputFromWindow(passwordEditTxt.windowToken, 0)
               if (emailTxt.text.toString().trim().equals("")) {
                   // enter valid email
                   CommonMethods.showDialogueWithOk(mContext, "Please enter Email.", "Alert")

               } else {
                   if (passwordEditTxt.text.toString().trim().equals("")) {
                       //enter password
                       CommonMethods.showDialogueWithOk(mContext, "Please enter Password.", "Alert")
                   } else {
                       var emailPattern = CommonMethods.isEmailValid(emailTxt.text.toString().trim())
                       if (!emailPattern) {
                           //enter valid email
                           CommonMethods.showDialogueWithOk(mContext, "Please enter a valid Email.", "Alert")
                       } else {
                           if (CommonMethods.isInternetAvailable(mContext)) {
                               progressDialog.visibility=View.VISIBLE
                               callLoginApi(emailTxt.text.toString().trim(), passwordEditTxt.text.toString().trim()
                               )
                           } else {
                               CommonMethods.showSuccessInternetAlert(mContext)
                           }
                       }
                   }
               }
           }

        })
        emailHelpImg.setOnClickListener(View.OnClickListener {

            if (CommonMethods.isInternetAvailable(mContext)) {
                val deliveryAddress =
                    arrayOf("communications@nasdubai.ae")
                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.putExtra(Intent.EXTRA_EMAIL, deliveryAddress)
                emailIntent.type = "text/plain"
                emailIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val pm: PackageManager = emailHelpImg.context.packageManager
                val activityList = pm.queryIntentActivities(
                    emailIntent, 0
                )
                for (app in activityList) {
                    if (app.activityInfo.name.contains("com.google.android.gm")) {
                        val activity = app.activityInfo
                        val name = ComponentName(
                            activity.applicationInfo.packageName, activity.name
                        )
                        emailIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                        emailIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                                or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
                        emailIntent.component = name
                        emailHelpImg.context.startActivity(emailIntent)
                        break
                    }
                }
            } else {
                // No internet alert
                CommonMethods.showSuccessInternetAlert(mContext)
            }

        })
    }

    @SuppressLint("ClickableViewAccessibility")
    fun showSignupDialog(context: Context)
    {
        var dialog:Dialog = Dialog(mContext, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_signu_up)
        var text_dialog = dialog.findViewById(R.id.text_dialog) as? EditText
        var btn_maybelater = dialog.findViewById(R.id.btn_maybelater) as? Button
        var btn_signup = dialog.findViewById(R.id.btn_signup) as? Button
        var progressDialog:RelativeLayout=dialog.findViewById(R.id.progressDialog)
        progressDialog.visibility= View.GONE
        btn_maybelater?.text = "Maybe later";
        text_dialog?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                text_dialog.isFocusable = false
                text_dialog.isFocusableInTouchMode = false
                false
            } else {
                text_dialog.isFocusable = false
                text_dialog.isFocusableInTouchMode = false
                false
            }
        }
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        btn_signup?.setOnClickListener()
        {
            if (text_dialog?.text.toString().trim().equals("")) {
                CommonMethods.showDialogueWithOk(mContext, "Please enter Email.", "Alert")
            } else {

                val emailPattern =
                    CommonMethods.isEmailValid(text_dialog?.text.toString().trim())
                if (!emailPattern) {
                    CommonMethods.showDialogueWithOk(mContext, "Please enter a valid Email.", "Alert")
                } else {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(text_dialog?.windowToken, 0)
                    val internetCheck = CommonMethods.isInternetAvailable(mContext)
                    if (internetCheck) {
                        progressDialog.visibility= View.VISIBLE
                        callSignUpApi(text_dialog?.text.toString().trim(), dialog,progressDialog)

                    } else {
                        CommonMethods.showSuccessInternetAlert(mContext)
                    }


                }
            }
        }
        text_dialog?.setOnTouchListener { v, m -> // Perform tasks here
            text_dialog.isFocusable = true
            text_dialog.isFocusableInTouchMode = true
            false
        }
        btn_maybelater?.setOnClickListener()
        {
            dialog.dismiss()
            isClicked=false
        }
        dialog.show()

    }

    fun callSignUpApi(email:String,dialog: Dialog,progressDialog:RelativeLayout)
    {
        progressDialog.visibility= View.VISIBLE
        val call: Call<ResponseBody> = ApiClient.getClient.registeruser(email)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressDialog.visibility= View.GONE
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressDialog.visibility= View.GONE
                val responsedata = response.body()
                if (responsedata != null) {
                    try {
                        val jsonObject = JSONObject(responsedata.string())
                        if (jsonObject.has("status")) {
                            val status: Int = jsonObject.optInt("status")
                            val message: String = jsonObject.optString("message")
                            if (status == 100) {
                                isClicked=false
                                showSuccessAlertForgot(mContext, "Successfully registered.Please check your Email for further details  .", "Success", dialog,"close")

                            }
                            else
                            {
                                isClicked=true
                                showSuccessAlertForgot(mContext, message, "Alert", dialog,"open")

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }


    fun showSuccessAlertForgot(context: Context, message: String, msgHead: String, dialogPassword: Dialog,test:String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_ok_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as? TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as? TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as? Button
        text_dialog?.text = message
        Log.e("printeddata:", message)
        alertHead?.text = msgHead
        iconImageView?.setImageResource(R.drawable.exclamationicon)
        btn_Ok?.setOnClickListener()
        {
            if(test.equals("close"))
            {
                dialogPassword.dismiss()
                dialog.dismiss()
            }
            else{
                dialog.dismiss()
            }

        }
        dialog.show()
    }



    @SuppressLint("ClickableViewAccessibility")
    fun showForgotPassword(context: Context)
    {
        var dialog:Dialog = Dialog(mContext, R.style.NewDialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_forgot_password)
        var text_dialog = dialog.findViewById(R.id.text_dialog) as? EditText
        var btn_maybelater = dialog.findViewById(R.id.btn_maybelater) as? Button
        var btn_signup = dialog.findViewById(R.id.btn_signup) as? Button
        var progressDialog:RelativeLayout=dialog.findViewById(R.id.progressDialog)
        progressDialog.visibility= View.GONE
        btn_maybelater?.text = "Maybe later";
        text_dialog?.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                text_dialog.isFocusable = false
                text_dialog.isFocusableInTouchMode = false
                false
            } else {
                text_dialog.isFocusable = false
                text_dialog.isFocusableInTouchMode = false
                false
            }
        }
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        btn_signup?.setOnClickListener()
        {
            if (text_dialog?.text.toString().trim().equals("")) {
                CommonMethods.showDialogueWithOk(mContext, "Please enter Email.", "Alert")
            } else {

                val emailPattern =
                    CommonMethods.isEmailValid(text_dialog?.text.toString().trim())
                if (!emailPattern) {
                    CommonMethods.showDialogueWithOk(mContext, "Please enter a valid Email.", "Alert")
                } else {
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(text_dialog?.windowToken, 0)
                    val internetCheck = CommonMethods.isInternetAvailable(mContext)
                    if (internetCheck) {
                        progressDialog.visibility= View.VISIBLE
                        callForgotPassword(text_dialog?.text.toString().trim(), dialog,progressDialog)

                    } else {
                        CommonMethods.showSuccessInternetAlert(mContext)
                    }


                }
            }
        }
        text_dialog?.setOnTouchListener { v, m -> // Perform tasks here
            text_dialog.isFocusable = true
            text_dialog.isFocusableInTouchMode = true
            false
        }
        btn_maybelater?.setOnClickListener()
        {
            dialog.dismiss()
            isClicked=false
        }
        dialog.show()

    }


    fun callForgotPassword(email:String,dialog: Dialog,progressDialog:RelativeLayout)
    {
        progressDialog.visibility= View.VISIBLE
        val call: Call<ResponseBody> = ApiClient.getClient.forgotpassword(email)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                progressDialog.visibility= View.GONE
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                progressDialog.visibility= View.GONE
                val responsedata = response.body()
                if (responsedata != null) {
                    try {
                        val jsonObject = JSONObject(responsedata.string())
                        if (jsonObject.has("status")) {
                            val status: Int = jsonObject.optInt("status")
                            val message: String = jsonObject.optString("message")
                            if (status == 100) {
                                isClicked=false
                                showSuccessAlertForgot(mContext, "Password is successfully sent to your email. Please check.", "Success", dialog,"close")

                            }
                            else
                            {
                                isClicked=true
                                showSuccessAlertForgot(mContext, message, "Alert", dialog,"open")

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }
//    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        when (v) {
//            emailTxt -> {
//                when (event!!.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        emailTxt?.isFocusable = true
//                        emailTxt?.isFocusableInTouchMode = true
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        v.performClick()
//                        emailTxt?.isFocusable = true
//                        emailTxt?.isFocusableInTouchMode = true
//                    }
//                }
//            }
//            passwordEditTxt -> {
//                when (event!!.action) {
//                    MotionEvent.ACTION_DOWN -> {
//                        passwordEditTxt?.isFocusable = true
//                        passwordEditTxt?.isFocusableInTouchMode = true
//                    }
//                    MotionEvent.ACTION_UP -> {
//                        v.performClick()
//                        passwordEditTxt?.isFocusable = true
//                        passwordEditTxt?.isFocusableInTouchMode = true
//                    }
//                }
//            }
//        }
//        return false
//    }

    @SuppressLint("HardwareIds")
    fun callLoginApi(email: String, password: String) {
        progressDialog.visibility=View.VISIBLE
        var androidID = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
      //  System.out.println("LOGINRESPONSE:"+"email:"+email+"pass:"+password+"devid:  "+androidID+" FCM ID : "+ FirebaseInstanceId.getInstance().token.toString())
        val call: Call<LoginResponse> = ApiClient.getClient.login(email, password, 2, "123456789", androidID)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressDialog.visibility=View.GONE
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responsedata = response.body()
                progressDialog.visibility=View.GONE
                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                    try {
                        val status=responsedata.status
                        Log.e("STATUS", status.toString())
                        if (status==100)
                        {
                            PreferenceManager.setUserCode(mContext,responsedata.data.token)
                            PreferenceManager.setUserEmail(mContext,responsedata.data.user_details.email)
                            dialogueWithOk(mContext,"Successfully Logged in","Alert","success")
                        }
                        else
                        {
                          //  dialogueWithOk(mContext,responsedata.message,"Alert","error")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })

    }


    fun dialogueWithOk(context: Context,title:String,description:String,action:String)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialogue_ok_alert)
        dialog.getWindow()!!.setBackgroundDrawableResource(android.R.color.transparent)
        val btn_Ok=dialog.findViewById<Button>(R.id.btn_Ok)
        val descriptionTxt=dialog.findViewById<TextView>(R.id.text_dialog)
        val titleTxt=dialog.findViewById<TextView>(R.id.alertHead)
        val iconImageView=dialog.findViewById<ImageView>(R.id.iconImageView)
        titleTxt.text=description
        descriptionTxt.text=title
        iconImageView.setImageResource(R.drawable.exclamationicon)
        btn_Ok.setOnClickListener(View.OnClickListener {
            if(action.equals("success"))
            {
                isClicked=false
                startActivity(Intent(context, HomeActivity::class.java))
                dialog.dismiss()
                finish()
            }
            else{
                isClicked=false
                dialog.dismiss()
            }


        })
        dialog.show()

    }
}