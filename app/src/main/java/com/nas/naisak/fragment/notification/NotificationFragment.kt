package com.nas.naisak.fragment.notification

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activity.notification.ImageNotificationActivity
import com.nas.naisak.activity.notification.TextNotificationActivity
import com.nas.naisak.activity.notification.VideoNotificationActivity
import com.nas.naisak.activtiy.notification.AudioNotification
import com.nas.naisak.commonmodels.ModelWithPageNumberOnly
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.notification.adapter.NotificationListAdapter
import com.nas.naisak.fragment.notification.model.NotificationListResponse
import com.nas.naisak.fragment.notification.model.NotificationResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {
    lateinit var mContext: Context
    lateinit var titleTextView: TextView
    lateinit var notificationRecycler: RecyclerView
    lateinit var progressDialog: RelativeLayout
    lateinit var linearLayoutManager: LinearLayoutManager
    var notificationList = ArrayList<NotificationListResponse>()


    var stopLoading:Boolean=false
    var isLoading:Boolean=false
    var pagenumber:Int=1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()

    }

    @SuppressLint("WrongViewCast")
    private fun initialiseUI() {
        mContext = requireContext()
        titleTextView = view?.findViewById(R.id.titleTextView) as TextView
        titleTextView.text="Notifications"
        notificationRecycler = view?.findViewById(R.id.notificationRecycler) as RecyclerView
        progressDialog = view?.findViewById(R.id.progressDialog) as RelativeLayout
        linearLayoutManager = LinearLayoutManager(mContext)
        notificationRecycler.layoutManager = linearLayoutManager
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        if (CommonMethods.isInternetAvailable(mContext)) {
            pagenumber=1
            notificationList= ArrayList()
            progressDialog.visibility=View.VISIBLE
            getNotificationList(pagenumber)

        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }

        notificationRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(@NonNull recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(
                @NonNull recyclerView: RecyclerView, dx: Int, dy: Int
            ) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == notificationList.size - 1) {
                        //bottom of list!
                        if (!stopLoading) {
                            pagenumber = pagenumber + 1
                            getNotificationList(pagenumber)
                            isLoading = true
                        }

                    }
                }
            }
        })
        notificationRecycler.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                if (notificationList.get(position).alertType.equals("Text"))
                {
                    val intent = Intent(activity, TextNotificationActivity::class.java)
                    intent.putExtra("id",notificationList.get(position).id.toString())
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }
                else if (notificationList.get(position).alertType.equals("Image"))
                {
                    val intent = Intent(activity, ImageNotificationActivity::class.java)
                    intent.putExtra("id",notificationList.get(position).id.toString())
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }
                else if (notificationList.get(position).alertType.equals("Video"))
                {
                    val intent = Intent(activity, VideoNotificationActivity::class.java)
                    intent.putExtra("id",notificationList.get(position).id.toString())
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }
                else if (notificationList.get(position).alertType.equals("Voice"))
                {
                    val intent = Intent(activity, AudioNotification::class.java)
                    intent.putExtra("id",notificationList.get(position).id.toString())
                    intent.putExtra("title",notificationList.get(position).title)
                    activity?.startActivity(intent)
                }

            }

        })

    }

    private fun getNotificationList(pageno:Int) {
        var notificationSize:Int=0
        progressDialog.visibility=View.VISIBLE
        val token = PreferenceManager.getUserCode(mContext)
        var sizeArray:Int=0
        var earlyyearscominguplist = ArrayList<NotificationListResponse>()
        val noticationpage = ModelWithPageNumberOnly(pageno.toString())
        val call: Call<NotificationResponseModel> =
            ApiClient.getClient.notificationList(noticationpage, "Bearer " + token)
        call.enqueue(object : Callback<NotificationResponseModel> {
            override fun onFailure(call: Call<NotificationResponseModel>, t: Throwable) {
                progressDialog.visibility=View.GONE

            }

            override fun onResponse(
                call: Call<NotificationResponseModel>,
                response: Response<NotificationResponseModel>
            )
            {
                progressDialog.visibility=View.GONE
                if (response.body()!!.status==100){

                    if (response.body()!!.data.lists.size>0)
                    {
                        earlyyearscominguplist.addAll(response.body()!!.data.lists)
                        sizeArray = earlyyearscominguplist.size
                        notificationList.addAll(earlyyearscominguplist)

                        if (earlyyearscominguplist.size >= 15) {
                            stopLoading = false
                        } else {
                            stopLoading = true
                        }


                    }

                    if (notificationList.size>0)
                    {

                        notificationRecycler.visibility=View.VISIBLE
                        val messageListAdapter = NotificationListAdapter(notificationList)
                        notificationRecycler.setAdapter(messageListAdapter)
                        isLoading = false
                        if (notificationList.size>=15)
                        {
                            if (sizeArray>0)
                            {
                                notificationRecycler.scrollToPosition(notificationList.size-sizeArray)
                            }
                        }
                    }
                    else{

                        notificationRecycler.visibility=View.GONE
                    }
                }
            }

        })
    }

}