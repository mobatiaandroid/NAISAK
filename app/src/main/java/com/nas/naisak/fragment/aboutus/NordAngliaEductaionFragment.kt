package com.nas.naisak.fragment.aboutus

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.webkit.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activity.notification.ImageNotificationActivity
import com.nas.naisak.activity.notification.TextNotificationActivity
import com.nas.naisak.activity.notification.VideoNotificationActivity
import com.nas.naisak.commonmodels.ModelWithPageNumberOnly
import com.nas.naisak.commonmodels.StudentListModel
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.WebviewLoader
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.aboutus.model.NAEResponseModel
import com.nas.naisak.fragment.notification.adapter.NotificationListAdapter
import com.nas.naisak.fragment.notification.model.NotificationListResponse
import com.nas.naisak.fragment.notification.model.NotificationResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NordAngliaEductaionFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var progressDialog: RelativeLayout
    lateinit var webview: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nord_anglia_education, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            progressDialog.visibility= View.VISIBLE
            getNAEDetails()

        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
    }

    @SuppressLint("WrongViewCast")
    private fun initialiseUI() {
        mContext = requireContext()
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        titleTextView.text="Nord Anglia Educations"
        progressDialog = view?.findViewById(R.id.progressDialog) as RelativeLayout
        webview = view?.findViewById(R.id.webview) as WebView
        webview.settings.setJavaScriptEnabled(true)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)


        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }

    }

    private fun getNAEDetails() {
        progressDialog.visibility= View.VISIBLE
        val token = PreferenceManager.getUserCode(mContext)
        val call: Call<NAEResponseModel> =
            ApiClient.getClient.naeDetails( "Bearer " + token)
        call.enqueue(object : Callback<NAEResponseModel> {
            override fun onFailure(call: Call<NAEResponseModel>, t: Throwable) {
                progressDialog.visibility= View.GONE

            }

            override fun onResponse(
                call: Call<NAEResponseModel>,
                response: Response<NAEResponseModel>
            )
            {
                progressDialog.visibility= View.GONE
                if (response.body()!!.status==100){

                    //var url=response.body()!!.data.list.url
                     var url=response.body()!!.data.list.get(0).url
                //    var url="https://www.nordangliaeducation.com/"
                    Log.e("URL",url)
                    webview.loadUrl(url)
                }
            }

        })
    }
}