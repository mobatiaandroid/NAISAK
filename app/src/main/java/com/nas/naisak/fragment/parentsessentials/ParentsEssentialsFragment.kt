package com.nas.naisak.fragment.parentsessentials

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
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.WebviewLoader
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.aboutus.model.NAEListModel
import com.nas.naisak.fragment.aboutus.model.NAEResponseModel
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialListModel
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialResponseModel
import com.nas.naisak.fragment.parentsessentials.adapter.ParentsEssentialListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ParentsEssentialsFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var mListView: RecyclerView
    lateinit var progressDialog: RelativeLayout
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var parentsEssentialArrayList : ArrayList<ParentsEssentialListModel>
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
        mListView=view!!.findViewById(R.id.mListView)
        progressDialog=view!!.findViewById(R.id.progressDialog)
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        bannerImagePager=view!!.findViewById(R.id.bannerImagePager)
        sendEmail=view!!.findViewById(R.id.sendEmail)
        descriptionTV=view!!.findViewById(R.id.descriptionTV)
        titleTextView.text="Parents Essentials"
        linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)

        mListView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                val intent = Intent(mContext, WebviewLoader::class.java)
                intent.putExtra("webview_url", parentsEssentialArrayList[position].url)
                intent.putExtra("title", parentsEssentialArrayList[position].title)
                startActivity(intent)

            }

        })
    }

    private fun callParentsEssentialBanner() {
        progressDialog.visibility = View.VISIBLE
        parentsEssentialArrayList= ArrayList()
        val call: Call<ParentsEssentialResponseModel> = ApiClient.getClient.parentessentials("Bearer "+PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<ParentsEssentialResponseModel> {
            override fun onFailure(call: Call<ParentsEssentialResponseModel>, t: Throwable) {
                progressDialog.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<ParentsEssentialResponseModel>,
                response: Response<ParentsEssentialResponseModel>
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

                    val parentsEssentialAdapter = ParentsEssentialListAdapter(mContext,parentsEssentialArrayList)
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