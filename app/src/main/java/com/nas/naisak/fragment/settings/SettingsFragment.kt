package com.nas.naisak.fragment.settings

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.BuildConfig
import com.nas.naisak.R
import com.nas.naisak.activtiy.tutorial.TutorialActivity
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.home.model.LogoutResponseModel
import com.nas.naisak.fragment.settings.adapter.SettingsRecyclerAdapter
import com.nas.naisak.fragment.settings.model.ChangePasswordApiModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var versionText: TextView
    lateinit var notificationRecycler: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var mSettingsArrayListRegistered : ArrayList<String>
    lateinit var mSettingsArrayListGuest: ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()

    }

    private fun initialiseUI() {
        mContext = requireContext()
        mSettingsArrayListRegistered= ArrayList()
        mSettingsArrayListGuest=ArrayList()
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        versionText = view?.findViewById(R.id.versionText) as TextView
        notificationRecycler = view?.findViewById(R.id.notificationRecycler) as RecyclerView
        titleTextView.text="Settings"
        var version:String=BuildConfig.VERSION_NAME
        versionText.setText("UAT : "+version)
        linearLayoutManager = LinearLayoutManager(mContext)
        notificationRecycler.layoutManager = linearLayoutManager
        if(PreferenceManager.getUserCode(mContext).equals(""))
        {
            mSettingsArrayListGuest.add("Change App Settings")
            mSettingsArrayListGuest.add("Terms of Service")
            mSettingsArrayListGuest.add("Email Us")
            mSettingsArrayListGuest.add("Tutorial")
        }
        else{
            mSettingsArrayListRegistered.add("Change App Settings")
            mSettingsArrayListRegistered.add("Terms of Service")
            mSettingsArrayListRegistered.add("Email Us")
            mSettingsArrayListRegistered.add("Tutorial")
            mSettingsArrayListRegistered.add("Change Password")
        }

        if (PreferenceManager.getUserCode(mContext).equals(""))
        {
            val settingsAdapter = SettingsRecyclerAdapter(mSettingsArrayListGuest)
            notificationRecycler.adapter = settingsAdapter
        }
        else{
            val settingsAdapter = SettingsRecyclerAdapter(mSettingsArrayListRegistered)
            notificationRecycler.adapter = settingsAdapter
        }
        notificationRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                if (PreferenceManager.getUserCode(mContext).equals("")) {
                    // change App Settings
                    if (position == 0) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity!!.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }
                    // Terms Of Service
                    else if (position == 1) {
                        val intent = Intent(activity, TermsOfServiceActivity::class.java)
                        activity?.startActivity(intent)
                    }
                    // Email Help
                    else if (position == 2)
                    {
                        val to = "communications@nasdubai.ae"
                        val email = Intent(Intent.ACTION_SEND)
                        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
                        email.type = "message/rfc822"
                        startActivity(Intent.createChooser(email, "Choose an Email client :"))
                    }
                    //Tutorial
                    else if (position==3)
                    {
                        val intent =Intent(activity, TutorialActivity::class.java)
                        activity?.startActivity(intent)

                    }

                }
                else{
                    // change App Settings
                    if (position == 0) {
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package", activity!!.packageName, null)
                        intent.data = uri
                        mContext.startActivity(intent)
                    }
                    // Terms Of Service
                    else if (position == 1) {
                        val intent = Intent(activity, TermsOfServiceActivity::class.java)
                        activity?.startActivity(intent)
                    }
                    // Email Help
                    else if (position == 2)
                    {
                        val to = "communications@nasdubai.ae"
                        val email = Intent(Intent.ACTION_SEND)
                        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
                        email.type = "message/rfc822"
                        startActivity(Intent.createChooser(email, "Choose an Email client :"))
                    }
                    //Tutorial
                    else if (position==3)
                    {
                        val intent =Intent(activity, TutorialActivity::class.java)
                        activity?.startActivity(intent)
                    }
                    // Change Password
                    else if (position==4)
                    {

                        showChangePasswordDialogue(mContext)
                    }

                }


            }

        })

    }


    @SuppressLint("WrongViewCast", "ClickableViewAccessibility")
    fun showChangePasswordDialogue(context: Context)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialogue_change_password)
        var text_currentpassword = dialog.findViewById(R.id.text_currentpassword) as EditText
        var text_currentnewpassword = dialog.findViewById(R.id.text_currentnewpassword) as EditText
        var text_confirmpassword = dialog.findViewById(R.id.text_confirmpassword) as EditText
        var btn_changepassword = dialog.findViewById(R.id.btn_changepassword) as Button
        var progressDialog = dialog.findViewById(R.id.progressDialog) as ProgressBar
        var btn_cancel = dialog.findViewById(R.id.btn_cancel) as Button
        btn_cancel.isClickable=true


        btn_changepassword?.alpha=0.5f
        btn_changepassword?.isClickable=false
        var isSigned:Boolean=false


        text_currentpassword?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentnewpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_confirmpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentnewpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_confirmpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentnewpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_confirmpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }
        })

         text_currentnewpassword?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_confirmpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_confirmpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_confirmpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }
        })

        text_confirmpassword?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_currentnewpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_currentnewpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString().trim().equals(""))
                {
                    btn_changepassword?.alpha=0.5f
                    btn_changepassword?.isClickable=false
                    isSigned=false
                }
                else{
                    if(text_currentpassword.text.toString().trim().equals(""))
                    {
                        btn_changepassword?.alpha=0.5f
                        btn_changepassword?.isClickable=false
                        isSigned=false
                    }
                    else{
                        if(text_currentnewpassword.text.toString().trim().equals(""))
                        {
                            btn_changepassword?.alpha=0.5f
                            btn_changepassword?.isClickable=false
                            isSigned=false
                        }
                        else{
                            btn_changepassword?.alpha=1.0f
                            btn_changepassword?.isClickable=true
                            isSigned=true
                        }
                    }

                }
            }
        })



        btn_cancel.setOnClickListener()
        {

            dialog.dismiss()
        }

        btn_changepassword.setOnClickListener()
        {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(text_currentpassword.windowToken, 0)
            val imn= context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imn.hideSoftInputFromWindow(text_currentnewpassword.windowToken, 0)
            val imo= context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imo.hideSoftInputFromWindow(text_confirmpassword.windowToken, 0)
            if (text_currentpassword.text.toString().trim().equals(""))
            {
                CommonMethods.showDialogueWithOk(mContext, "Please enter Current Password.", "Alert")
            } else{
                if (text_currentnewpassword.text.toString().trim().equals("")) {
                    CommonMethods.showDialogueWithOk(mContext, "Please enter New Password.", "Alert")
                } else{
                    if (text_confirmpassword.text.toString().trim().equals("")) {
                        CommonMethods.showDialogueWithOk(mContext, "Please enter Current Password.", "Alert")
                    } else{
                        callChangePasswordApi(text_currentpassword.text.toString().trim(),text_currentnewpassword.text.toString().trim(),text_confirmpassword.text.toString(),dialog,progressDialog)
                    }
                }
            }

        }


        dialog.show()
    }



    fun callChangePasswordApi(currentPassword:String, newPassword:String, confirmPassword:String, dialog: Dialog, progressBar: ProgressBar) {
        progressBar.visibility= View.VISIBLE
        val token = PreferenceManager.getUserCode(mContext)
        val changePasswordBody = ChangePasswordApiModel(newPassword, confirmPassword, currentPassword)
        val call: Call<LogoutResponseModel> =
            ApiClient.getClient.changePassword(changePasswordBody, "Bearer " + token)
        call.enqueue(object : Callback<LogoutResponseModel> {
            override fun onFailure(call: Call<LogoutResponseModel>, t: Throwable) {
                Log.e("Failed", t.localizedMessage)
                progressBar.visibility= View.GONE
            }

            override fun onResponse(call: Call<LogoutResponseModel>, response: Response<LogoutResponseModel>) {
                val responsedata = response.body()
                progressBar.visibility= View.GONE
                Log.e("Response Signup", responsedata.toString())
                if (responsedata != null) {
                    try {
                      if(response.body()!!.status==100)
                      {
                          showChangePasswordSuccessDialogue(mContext,dialog)
                      }
                        else
                      {
                          if (response.body()!!.validationErrorArray.size>0)
                          {
                              val message=response.body()!!.validationErrorArray.get(0).toString()
                              CommonMethods.showDialogueWithOk(mContext,message,"Alert")
                          }
                          else{
                              val msg=response.body()!!.message
                              CommonMethods.showDialogueWithOk(mContext,msg,"Alert")
                          }
                      }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    fun showChangePasswordSuccessDialogue(context: Context,dialogNew: Dialog)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_ok_alert)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
        var alertHead = dialog.findViewById(R.id.alertHead) as? TextView
        var text_dialog = dialog.findViewById(R.id.text_dialog) as? TextView
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        text_dialog?.text = "Password successfully changed"
        alertHead?.text = "Success"
        iconImageView?.setImageResource(R.drawable.tick)
        btn_Ok.setOnClickListener()
        {
            dialog.dismiss()
            dialogNew.dismiss()
        }
        dialog.show()
    }
}