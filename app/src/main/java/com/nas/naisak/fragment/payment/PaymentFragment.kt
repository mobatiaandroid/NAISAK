package com.nas.naisak.fragment.payment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nas.naisak.R
import com.nas.naisak.activtiy.payment.information.PaymentInformationActivity
import com.nas.naisak.activtiy.payment.payhere.PaymentActivity
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.fragment.payment.model.PaymentBannerResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var bannerImagePager: ImageView
    lateinit var sendEmail: ImageView
    lateinit var descriptionTV: TextView
    lateinit var progressDialog: RelativeLayout
    lateinit var paymentRelative: RelativeLayout
    lateinit var informationRelative: RelativeLayout
    lateinit var title: RelativeLayout
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var titleTextView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_payment, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            callPaymentBanner()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
    }

    @SuppressLint("SetTextI18n", "UseRequireInsteadOfGet")
    private fun initialiseUI() {
        mContext = requireContext()
        bannerImagePager=view!!.findViewById(R.id.bannerImagePager)
        sendEmail=view!!.findViewById(R.id.sendEmail)
        descriptionTV=view!!.findViewById(R.id.descriptionTV)
        progressDialog=view!!.findViewById(R.id.progressDialog)
        paymentRelative=view!!.findViewById(R.id.paymentRelative)
        informationRelative=view!!.findViewById(R.id.informationRelative)
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        titleTextView.text="Payment"
        title=view!!.findViewById(R.id.title)
        linearLayoutManager = LinearLayoutManager(mContext)
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

        paymentRelative.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, PaymentActivity::class.java)
            activity?.startActivity(intent)
//            CommonMethods.showDialogueWithOk(mContext, "Comming Soon", "Alert")
        })

        informationRelative.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, PaymentInformationActivity::class.java)
            activity?.startActivity(intent)
        })

        sendEmail.setOnClickListener(View.OnClickListener {


        })

    }

    private fun callPaymentBanner() {
        progressDialog.visibility = View.VISIBLE
        val call: Call<PaymentBannerResponse> = ApiClient.getClient.paymentBanner("Bearer "+PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<PaymentBannerResponse> {
            override fun onFailure(call: Call<PaymentBannerResponse>, t: Throwable) {
                progressDialog.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<PaymentBannerResponse>,
                response: Response<PaymentBannerResponse>
            ) {
                progressDialog.visibility = View.GONE

                if (response.body()!!.status == 100) {

                    val email:String=response.body()!!.data.contact_email
                    val description:String=response.body()!!.data.description
                    val bannerImage:String=response.body()!!.data.banner_image

                    if (bannerImage.isNotEmpty()){
                        context?.let {
                            Glide.with(it)
                                .load(bannerImage)
                                .into(bannerImagePager)
                        }!!
                    }else{
                        bannerImagePager.setBackgroundResource(R.drawable.default_banner)

                    }
                    if (email.equals(""))
                    {
                        sendEmail.visibility= View.GONE
                        title.visibility= View.GONE
                    }
                    else{
                        sendEmail.visibility= View.VISIBLE
                        title.visibility= View.VISIBLE
                    }
                    if (description.equals(""))
                    {
                        descriptionTV.visibility= View.GONE
                    }
                    else{
                        descriptionTV.visibility= View.VISIBLE
                        descriptionTV.setText(description)
                    }

                }

                else {
                    if (response.body()!!.status == 101) {
                        CommonMethods.showDialogueWithOk(mContext, "Some error occured", "Alert")
                    }
                }

            }

        })
    }

}