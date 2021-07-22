package com.nas.naisak.constants

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.nas.naisak.R

class CommonMethods {
    companion object{

        fun isInternetAvailable(context: Context): Boolean
        {
            var result = false
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cm?.run {
                    cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                        result = when {
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                            else -> false
                        }
                    }
                }
            } else {
                cm?.run {
                    cm.activeNetworkInfo?.run {
                        if (type == ConnectivityManager.TYPE_WIFI) {
                            result = true
                        } else if (type == ConnectivityManager.TYPE_MOBILE) {
                            result = true
                        }
                    }
                }
            }
            return result
        }


        fun showSuccessInternetAlert(context: Context)
        {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialogue_no_internet)
            var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
            var alertHead = dialog.findViewById(R.id.alertHead) as TextView
            var text_dialog = dialog.findViewById(R.id.text_dialog) as TextView
            var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
            text_dialog.text = "Network error occurred. Please check your internet connection and try again later"
            alertHead.text = "Alert"
            iconImageView.setBackgroundResource(R.drawable.roundred)
            iconImageView.setImageResource(R.drawable.nonetworkicon)
            btn_Ok?.setOnClickListener()
            {
                dialog.dismiss()

            }
            dialog.show()
        }

        fun showDialogueWithOk(context: Context,message : String,msgHead : String)
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
            text_dialog?.text = message
            alertHead?.text = msgHead
            btn_Ok.setOnClickListener()
            {
                dialog.dismiss()
            }
            dialog.show()
        }
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}