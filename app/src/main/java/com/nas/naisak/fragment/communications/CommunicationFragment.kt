package com.nas.naisak.fragment.communications

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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nas.naisak.R
import com.nas.naisak.activtiy.communication.SocialMediaActivity
import com.nas.naisak.commonmodels.ModelStatusMsgData
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.WebviewLoader
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.communications.adapter.CommunicationAdapter
import com.nas.naisak.fragment.communications.model.CommunicationListModel
import com.nas.naisak.fragment.communications.model.CommunicationListUseModel
import com.nas.naisak.fragment.communications.model.CommunicationResponseModel
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialListModel
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialResponseModel
import com.nas.naisak.fragment.parentsessentials.adapter.ParentsEssentialListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommunicationFragment  : Fragment() {
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var progressDialog: RelativeLayout
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var parentsEssentialArrayList : ArrayList<CommunicationListModel>
    lateinit var parentsEssentialArrayListUse : ArrayList<CommunicationListUseModel>
    lateinit var titleTextView: TextView
    lateinit var bannerImagePager:ImageView
    lateinit var sendEmail:ImageView
    lateinit var descriptionTV:TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parents_essentials, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            callParentsEssentialBanner()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initialiseUI() {
        mContext = requireContext()

        progressDialog=view!!.findViewById(R.id.progressDialog)
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        bannerImagePager=view!!.findViewById(R.id.bannerImagePager)
        sendEmail=view!!.findViewById(R.id.sendEmail)
        descriptionTV=view!!.findViewById(R.id.descriptionTV)
        titleTextView.text="Parents Essentials"
        mListView=view!!.findViewById(R.id.mListView)
        linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

        mListView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                if(position==0)
                {
                    val intent = Intent(mContext, SocialMediaActivity::class.java)
                    startActivity(intent)
                }
                else{
                    val intent = Intent(mContext, WebviewLoader::class.java)
                    intent.putExtra("webview_url", parentsEssentialArrayListUse[position].url)
                    startActivity(intent)
                }


            }

        })
    }

    private fun callParentsEssentialBanner() {
        progressDialog.visibility = View.VISIBLE
        parentsEssentialArrayList= ArrayList()
        parentsEssentialArrayListUse= ArrayList()
        val call: Call<CommunicationResponseModel> = ApiClient.getClient.communication("Bearer "+ PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<CommunicationResponseModel> {
            override fun onFailure(call: Call<CommunicationResponseModel>, t: Throwable) {
                progressDialog.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<CommunicationResponseModel>,
                response: Response<CommunicationResponseModel>
            ) {
                progressDialog.visibility = View.GONE

                if (response.body()!!.status == 100) {
                    var bannerImage=response.body()!!.data.banner_image
                    var contactEmail=response.body()!!.data.contact_email
                    var description=response.body()!!.data.description
                    parentsEssentialArrayList.addAll(response.body()!!.data.parents_essentials)
                    if (bannerImage.isNotEmpty()){
                        context?.let {
                            Glide.with(it)
                                .load(bannerImage)
                                .into(bannerImagePager)
                        }!!
                    }else{
                        bannerImagePager.setBackgroundResource(R.drawable.default_banner)

                    }
                    if (contactEmail.equals(""))
                    {
                        sendEmail.visibility=View.GONE
                    }
                    else{
                        sendEmail.visibility=View.VISIBLE
                    }
                    if (description.equals(""))
                    {
                        descriptionTV.visibility=View.GONE
                    }
                    else{
                        descriptionTV.visibility=View.VISIBLE
                        descriptionTV.setText(description)
                    }

                    if (parentsEssentialArrayList.size>0)
                    {
                        for (i in 0..parentsEssentialArrayList.size)
                        {
                            var model=CommunicationListUseModel()
                            if (i==0)
                            {
                               model.id=1001
                               model.title="Social Media"
                               model.url=""
                            }
                            else{
                                model.id=parentsEssentialArrayList.get(i-1).id
                                model.title=parentsEssentialArrayList.get(i-1).title
                                model.url=parentsEssentialArrayList.get(i-1).url
                            }

                            parentsEssentialArrayListUse.add(model)

                        }
                    }
                    else{
                        var model=CommunicationListUseModel()
                        model.id=1001
                        model.title="Social Media"
                        model.url=""
                        parentsEssentialArrayListUse.add(model)
                    }

                    val parentsEssentialAdapter = CommunicationAdapter(mContext,parentsEssentialArrayListUse)
                    mListView.adapter = parentsEssentialAdapter
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