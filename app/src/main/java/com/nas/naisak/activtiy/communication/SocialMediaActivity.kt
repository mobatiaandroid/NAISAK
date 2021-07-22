package com.nas.naisak.activtiy.communication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.naisak.R
import com.nas.naisak.activtiy.communication.adapter.SocialMediaAdapter
import com.nas.naisak.activtiy.communication.model.SocialMediaListModel
import com.nas.naisak.activtiy.communication.model.SocialMediaResponse
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.WebviewLoader
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SocialMediaActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var back: ImageView
    lateinit var backRelative: RelativeLayout
    lateinit var logoclick: ImageView
    lateinit var facebookButton: ImageView
    lateinit var twitterButton: ImageView
    lateinit var instagramButton: ImageView
    lateinit var bannerImageViewPager: ImageView
    lateinit var facebookArrayList: ArrayList<SocialMediaListModel>
    lateinit var twitterArrayList : ArrayList<SocialMediaListModel>
    lateinit var instagramArrayList : ArrayList<SocialMediaListModel>
    lateinit var socialMediaArrayList:ArrayList<SocialMediaListModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_media)
        InitUI()
        backRelative.setOnClickListener {
            finish()
        }

    }


    private fun InitUI() {
        mContext = this
        linearLayoutManager = LinearLayoutManager(mContext)
        titleTextView = findViewById(R.id.heading)
        back = findViewById(R.id.btn_left)
        backRelative = findViewById(R.id.backRelative)
        logoclick = findViewById(R.id.logoClickImgView)
        facebookButton = findViewById(R.id.facebookButton)
        twitterButton = findViewById(R.id.twitterButton)
        instagramButton = findViewById(R.id.instagramButton)
        bannerImageViewPager = findViewById(R.id.bannerImageViewPager)
        titleTextView.text = "Social Media"

        if (CommonMethods.isInternetAvailable(mContext)) {
            callSocialMediaApi()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
        logoclick.setOnClickListener {
            val mIntent = Intent(mContext, HomeActivity::class.java)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            startActivity(mIntent)
        }

        facebookButton.setOnClickListener(View.OnClickListener {

            if (facebookArrayList.size>0)
            {
                if(facebookArrayList.size==1)
                {
                    val intent = Intent(mContext, WebviewLoader::class.java)
                    intent.putExtra("webview_url", facebookArrayList.get(0).url)
                    startActivity(intent)

                }
                else{
                    socialMediaList(facebookArrayList,"facebook")
                }
            }
        })
        twitterButton.setOnClickListener(View.OnClickListener {

            if (twitterArrayList.size>0)
            {
                if(twitterArrayList.size==1)
                {
                    val intent = Intent(mContext, WebviewLoader::class.java)
                    intent.putExtra("webview_url", twitterArrayList.get(0).url)
                    startActivity(intent)
                }
                else{
                    socialMediaList(twitterArrayList,"twitter")
                }
            }

        })
        instagramButton.setOnClickListener(View.OnClickListener {

            if (instagramArrayList.size>0)
            {
                if(instagramArrayList.size==1)
                {
                    val intent = Intent(mContext, WebviewLoader::class.java)
                    intent.putExtra("webview_url", instagramArrayList.get(0).url)
                    startActivity(intent)
                }
                else{
                    socialMediaList(instagramArrayList,"instagram")
                }
            }
        })


    }

    fun callSocialMediaApi() {
        facebookArrayList= ArrayList()
        twitterArrayList=ArrayList()
        instagramArrayList=ArrayList()
        socialMediaArrayList=ArrayList()
        val call: Call<SocialMediaResponse> = ApiClient.getClient.socialmedia("Bearer "+PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<SocialMediaResponse> {
            override fun onFailure(call: Call<SocialMediaResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<SocialMediaResponse>,
                response: Response<SocialMediaResponse>
            ) {

                if (response.body()!!.status == 100) {
                    var banner_image:String=response.body()!!.data.banner_image
                    if (banner_image.equals("")){
                        mContext?.let {
                            Glide.with(it)
                                .load(banner_image)
                                .into(bannerImageViewPager)
                        }!!
                    }else{
                        bannerImageViewPager.setBackgroundResource(R.drawable.default_banner)

                    }
                    socialMediaArrayList=response.body()!!.data.detaillists
                    if (socialMediaArrayList.size>0)
                    {
                        for (i in 0..socialMediaArrayList.size-1)
                        {
                            if (socialMediaArrayList.get(i).title.contains("Facebook"))
                            {
                                facebookArrayList.add(socialMediaArrayList.get(i))
                            }
                            else if (socialMediaArrayList.get(i).title.contains("Twitter"))
                            {
                                twitterArrayList.add(socialMediaArrayList.get(i))
                            }
                            else if (socialMediaArrayList.get(i).title.contains("Instagram"))
                            {
                                instagramArrayList.add(socialMediaArrayList.get(i))
                            }

                        }


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


    fun socialMediaList(mediaList:ArrayList<SocialMediaListModel>,type:String)
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_social_media)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as ImageView
        var btn_dismiss = dialog.findViewById(R.id.btn_dismiss) as Button
        var recycler_view_social_media = dialog.findViewById(R.id.recycler_view_social_media) as RecyclerView
        var linearLayoutManager: LinearLayoutManager
        linearLayoutManager = LinearLayoutManager(mContext)
        recycler_view_social_media.layoutManager = linearLayoutManager
        recycler_view_social_media.itemAnimator = DefaultItemAnimator()
        val primaryadapter = SocialMediaAdapter(mediaList,mContext)
        recycler_view_social_media.adapter = primaryadapter
        val sdk = Build.VERSION.SDK_INT
        if (type.equals("facebook"))
        {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                iconImageView.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.roundfb)
                )
                btn_dismiss.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.buttonfb))
            } else {
                iconImageView.background = mContext.resources.getDrawable(R.drawable.roundfb)
                btn_dismiss.background = mContext.resources.getDrawable(R.drawable.buttonfb)
            }
        }
        else if (type.equals("instagram"))
        {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                iconImageView.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.roundins)
                )
                btn_dismiss.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.buttonins))
            } else {
                iconImageView.background = mContext.resources.getDrawable(R.drawable.roundins)
                btn_dismiss.background = mContext.resources.getDrawable(R.drawable.buttonins)
            }
        }
        else if (type.equals("twitter"))
        {
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                iconImageView.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.roundtw)
                )
                btn_dismiss.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.buttontwi))
            } else {
                iconImageView.background = mContext.resources.getDrawable(R.drawable.roundtw)
                btn_dismiss.background = mContext.resources.getDrawable(R.drawable.buttontwi)
            }
        }

        recycler_view_social_media.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, WebviewLoader::class.java)
                intent.putExtra("webview_url", mediaList[position].url)
                startActivity(intent)

            }

        })
        btn_dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }

        dialog.show()
    }
}