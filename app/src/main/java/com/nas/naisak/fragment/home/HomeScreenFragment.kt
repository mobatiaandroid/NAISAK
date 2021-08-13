package com.nas.naisak.fragment.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.activtiy.login.LoginActivity
import com.nas.naisak.constants.*
import com.nas.naisak.fragment.aboutus.NordAngliaEductaionFragment
import com.nas.naisak.fragment.calendar.CalendarFragment
import com.nas.naisak.fragment.communications.CommunicationFragment
import com.nas.naisak.fragment.communications.adapter.CommunicationAdapter
import com.nas.naisak.fragment.communications.model.CommunicationListUseModel
import com.nas.naisak.fragment.communications.model.CommunicationResponseModel
import com.nas.naisak.fragment.contactus.ContactUsFragment
import com.nas.naisak.fragment.home.model.Bannerresponse
import com.nas.naisak.fragment.home.model.HomeBadgeResponse
import com.nas.naisak.fragment.home.model.LogoutResponseModel
import com.nas.naisak.fragment.notification.NotificationFragment
import com.nas.naisak.fragment.parentsessentials.ParentsEssentialsFragment
import com.nas.naisak.fragment.payment.PaymentFragment
import com.nas.naisak.fragment.settings.SettingsFragment
import kotlinx.android.synthetic.main.fragment_homescreen.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

lateinit var relone: RelativeLayout
lateinit var reltwo: RelativeLayout
lateinit var relthree: RelativeLayout
lateinit var relfour: RelativeLayout
lateinit var relfive: RelativeLayout
lateinit var relsix: RelativeLayout
lateinit var relseven: RelativeLayout
lateinit var releight: RelativeLayout
lateinit var relnine: RelativeLayout
lateinit var reltxtnine: TextView

lateinit var relTxtone: TextView
lateinit var relTxttwo: TextView
lateinit var relTxtfive: TextView
lateinit var relTxtsix: TextView
lateinit var relTxtseven: TextView
lateinit var relTxteight: TextView
lateinit var relTxtthree: TextView
lateinit var relTxtfour: TextView



lateinit var relImgone: ImageView
lateinit var relImgtwo: ImageView
lateinit var relImgthree: ImageView
lateinit var relImgfour: ImageView
lateinit var relImgfive: ImageView
lateinit var relImgsix: ImageView
lateinit var relImgseven: ImageView
lateinit var relImgeight: ImageView
lateinit var relImgnine: ImageView
var versionfromapi: String = ""
var currentversion: String = ""


lateinit var mSectionText: Array<String?>
lateinit var homeActivity: HomeActivity
lateinit var appController: AppController
lateinit var listitems: Array<String>
lateinit var mListImgArrays: TypedArray
lateinit var TouchedView: View
//lateinit var TAB_ID: String
private var TAB_ID: String = ""
private var CLICKED: String = ""
private var INTENT_TAB_ID: String = ""
private var TABIDfragment: String = ""
private var usertype: String = ""
private var USERDATA: String = ""
private var previousTriggerType: Int = 0

lateinit var pager: ViewPager
var isDraggable: Boolean = false
lateinit var mContext: Context

class HomeScreenFragment : Fragment(), View.OnClickListener {

    var currentPage: Int = 0
    var bannerarray = ArrayList<String>()
    lateinit var relImgOneDot: TextView
    lateinit var relImgTwoDot: TextView
    lateinit var relImgThreeDot: TextView
    lateinit var relImgFourDot: TextView
    lateinit var relImgFiveDot: TextView
    lateinit var relImgSixDot: TextView
    lateinit var relImgSevenDot: TextView
    lateinit var relImgEightDot: TextView
    lateinit var relImgNineDot: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_homescreen, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet", "Recycle")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        homeActivity = activity as HomeActivity
        appController = AppController()
        CLICKED = homeActivity.sPosition.toString()
        listitems = resources.getStringArray(R.array.navigation_item_reg)
        mListImgArrays = context!!.resources.obtainTypedArray(R.array.navigation_item_reg_icons)
        initializeUI()
        getbannerimages()
        getBadgeApi()

        var internetCheck = CommonMethods.isInternetAvailable(mContext)
        setListeners()
        getButtonBgAndTextImages()

    }

    private fun getbannerimages() {
        bannerarray = ArrayList()
        val  call: Call<Bannerresponse> = ApiClient.getClient.bannerimages()
        call.enqueue(object :retrofit2.Callback<Bannerresponse>{
            override fun onFailure(call: Call<Bannerresponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<Bannerresponse>,
                response: Response<Bannerresponse>
            ) {
                if (response.body()!!.status==100){
                    bannerarray.addAll(response.body()!!.data.banner_images)

                    val handler = Handler()
                    val update = Runnable {
                        if (currentPage == bannerarray.size
                        ) {
                            currentPage = 0
                            pager.setCurrentItem(
                                currentPage,
                                false
                            )
                        } else {
                            pager
                                .setCurrentItem(currentPage++, true)
                        }
                    }
                    val swipeTimer = Timer()
                    swipeTimer.schedule(object : TimerTask() {
                        override fun run() {
                            handler.post(update)
                        }
                    }, 100, 3000)

                    val pageradapter = CustomPagerAdapter(mContext,bannerarray)
                    pager.adapter = pageradapter

                }
            }

        })

    }

    private fun setListeners() {
        relone.setOnClickListener(this)
        reltwo.setOnClickListener(this)
        relthree.setOnClickListener(this)
        relfour.setOnClickListener(this)
        relfive.setOnClickListener(this)
        relsix.setOnClickListener(this)
        relseven.setOnClickListener(this)
        releight.setOnClickListener(this)
        relnine.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        if (v == relone) {

            INTENT_TAB_ID = PreferenceManager.getButtonOneRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == reltwo) {

            INTENT_TAB_ID = PreferenceManager.getButtonTwoRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relthree) {

            INTENT_TAB_ID = PreferenceManager.getButtonThreeRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relfour) {

            INTENT_TAB_ID = PreferenceManager.getButtonFourRegTabID(
                mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relfive) {

            INTENT_TAB_ID = PreferenceManager.getButtonFiveRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relsix) {

            INTENT_TAB_ID = PreferenceManager.getButtonSixRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relseven) {

            INTENT_TAB_ID = PreferenceManager.getButtonSevenRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == releight) {

            INTENT_TAB_ID = PreferenceManager.getButtonEightRegTabID(mContext).toString()
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
        if (v == relnine) {

            INTENT_TAB_ID = NasTabConstants.TAB_LOGOUT_REG
            CHECKINTENTVALUE(INTENT_TAB_ID)
        }
    }

    private fun getButtonBgAndTextImages() {
        if (PreferenceManager.getButtonOneRegTextImage(mContext)!!.toInt() != 0) {
            relImgone.setImageResource(R.drawable.calendar)
            var relTwoStr: String? = ""
            relTwoStr =
                if (listitems[PreferenceManager
                        .getButtonOneRegTextImage(mContext)!!.toInt()].equals(
                        JsonConstants.CCAS,
                        ignoreCase = true
                    )
                ) {
                    JsonConstants.CCAS
                }
                else {
                    ClassNameConstants.CALENDAR
                }
            relTxtone.text = relTwoStr
            relTxtone.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relone.setBackgroundColor(
                PreferenceManager
                    .getButtonOneGuestBg(mContext)
            )

            if (PreferenceManager.getButtonOneRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgOneDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonOneRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgOneDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonOneRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonOneRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgOneDot.visibility= View.VISIBLE
                    relImgOneDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgOneDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }



        }
        if (PreferenceManager.getButtonTwoRegTextImage(mContext)!!.toInt() != 0
        ) {
            relImgtwo.setImageResource(R.drawable.naeprogramme)
            var relTwoStr: String? = ""
            relTwoStr =
                if (listitems[PreferenceManager
                        .getButtonTwoRegTextImage(mContext)!!.toInt()].equals(
                        JsonConstants.CCAS,
                        ignoreCase = true
                    )
                ) {
                    JsonConstants.CCAS
                } else {
                    ClassNameConstants.ABOUT_US
                }
            relTxttwo.text = relTwoStr
            relTxttwo.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            reltwo.setBackgroundColor(
                PreferenceManager
                    .getButtontwoGuestBg(mContext)
            )



            if (PreferenceManager.getButtonTwoRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgTwoDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonTwoRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgTwoDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonTwoRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonTwoRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgTwoDot.visibility= View.VISIBLE
                    relImgTwoDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgTwoDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }


        }
        if (PreferenceManager
                .getButtonThreeRegTextImage(mContext)!!.toInt() != 0
        ) {
            relImgthree.setImageResource(R.drawable.contacts)
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonThreeRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants.CCAS
            } else {
                ClassNameConstants.CONTACT_US
            }
            relTxtthree.text = relTwoStr
            relTxtthree.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relthree.setBackgroundColor(
                PreferenceManager
                    .getButtonthreeGuestBg(mContext)
            )


            if (PreferenceManager.getButtonThreeRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgThreeDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonThreeRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgThreeDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonThreeRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonThreeRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgThreeDot.visibility= View.VISIBLE
                    relImgThreeDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgThreeDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }

        }


        if (PreferenceManager
                .getButtonFourRegTextImage(mContext)!!.toInt() != 0
        ) {
            relImgfour.setImageResource(R.drawable.settings_side)
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonFourRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants.CCAS
            }
            else {
                ClassNameConstants.SETTINGS
            }
            relTxtfour.text = relTwoStr
            relTxtfour.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relfour.setBackgroundColor(
                PreferenceManager
                    .getButtonfourGuestBg(mContext)
            )


            if (PreferenceManager.getButtonFourRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgFourDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonFourRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgFourDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonFourRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonFourRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgFourDot.visibility= View.VISIBLE
                    relImgFourDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgFourDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }

        }


        if (PreferenceManager.getButtonFiveRegTextImage(mContext)!!.toInt() != 0)
        {
            relImgfive.setImageResource(R.drawable.notif)
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonFourRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants.CCAS
            } else {
                ClassNameConstants.NOTIFICATIONS
            }
            relTxtfive.text = relTwoStr
            relTxtfive.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relfive.setBackgroundColor(
                PreferenceManager
                    .getButtonfiveGuestBg(mContext)
            )


            if (PreferenceManager.getButtonFiveRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgFiveDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonFiveRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgFiveDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonFiveRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonFiveRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgFiveDot.visibility= View.VISIBLE
                    relImgFiveDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgFiveDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }


        }
        if (PreferenceManager.getButtonSixRegTextImage(mContext)!!.toInt() != 0) {
            relImgsix.setImageResource(R.drawable.parent
            )
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonSixRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants .CCAS
            }  else {
                ClassNameConstants.PARENT_ESSENTIALS
            }
            relTxtsix.text = relTwoStr
            relTxtsix.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relsix.setBackgroundColor(
                PreferenceManager
                    .getButtonsixGuestBg(mContext)
            )


            if (PreferenceManager.getButtonSixRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgSixDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonSixRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgSixDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonSixRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonSixRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgSixDot.visibility= View.VISIBLE
                    relImgSixDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgSixDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }


        }
        if (PreferenceManager
                .getButtonSevenRegTextImage(mContext)!!.toInt() != 0
        ) {
            relImgseven.setImageResource(R.drawable.communication
            )
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonSevenRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants.CCAS
            } else {
                ClassNameConstants.COMMUNICATIONS
            }
            relTxtseven.text = relTwoStr
            relTxtseven.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relseven.setBackgroundColor(
                PreferenceManager
                    .getButtonsevenGuestBg(mContext)
            )


            if (PreferenceManager.getButtonSevenRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgSevenDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonSevenRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgSevenDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonSevenRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonSevenRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgSevenDot.visibility= View.VISIBLE
                    relImgSevenDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgSevenDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }



        }
        if (PreferenceManager
                .getButtonEightRegTextImage(mContext)!!.toInt() != 0
        ) {
            relImgeight.setImageResource(R.drawable.payment
            )
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonEightRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants.CCAS
            } else {
                ClassNameConstants.PAYMENT
            }
            relTxteight.text = relTwoStr
            relTxteight.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            releight.setBackgroundColor(
                PreferenceManager
                    .getButtoneightGuestBg(mContext)
            )


            if (PreferenceManager.getButtonEightRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgEightDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonEightRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgEightDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonEightRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonEightRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgEightDot.visibility= View.VISIBLE
                    relImgEightDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgEightDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }



        }
        if (PreferenceManager
                .getButtonNineRegTextImage(mContext)!!.toInt() != 0
        ) {
            relImgnine.setImageResource(R.drawable.logout_side
            )
            var relTwoStr: String? = ""
            relTwoStr = if (listitems[PreferenceManager
                    .getButtonNineRegTextImage(mContext)!!.toInt()].equals(
                    JsonConstants.CCAS,
                    ignoreCase = true
                )
            ) {
                JsonConstants.CCAS
            } else {
                ClassNameConstants.LOGOUT
            }
            reltxtnine.text = relTwoStr
            reltxtnine.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            relnine.setBackgroundColor(
                PreferenceManager
                    .getButtonnineGuestBg(mContext)
            )


            if (PreferenceManager.getButtonNineRegTabID(mContext).equals(NasTabConstants.TAB_CALENDAR_REG))
            {
                if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.GONE
                }
                else if (PreferenceManager.getCalendarBadge(mContext)==0 && PreferenceManager.getCalendarEditedBadge(mContext)>0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getCalendarEditedBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getCalendarBadge(mContext)>0 && PreferenceManager.getCalendarEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getCalendarBadge(mContext)>1 && PreferenceManager.getCalendarEditedBadge(mContext)>1)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getCalendarBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else{
                    relImgNineDot.visibility= View.GONE
                }
            }
            else if (PreferenceManager.getButtonNineRegTabID(mContext).equals(NasTabConstants.TAB_NOTIFICATIONS_REG))
            {
                if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.GONE
                }
                else if (PreferenceManager.getNotificationBadge(mContext)==0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getNotificationEditedBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getNotificationBadge(mContext)>0 && PreferenceManager.getNotificationEditedBadge(mContext)>0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getNotificationBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else
                {
                    relImgNineDot.visibility= View.GONE
                }
            }

            else if (PreferenceManager.getButtonNineRegTabID(mContext).equals(NasTabConstants.TAB_COMMUNICATIONS_REG))
            {
                if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.GONE

                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)==0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {

                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getWholeSchoolEditedBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_navy)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)==0)
                {

                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getWholeSchoolBadge(mContext)>0 && PreferenceManager.getWholeSchoolEditedBadge(mContext)>0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getWholeSchoolBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }
            else if (PreferenceManager.getButtonNineRegTabID(mContext).equals(NasTabConstants.TAB_TRIPS_REG))
            {
                if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.GONE

                }
                else if (PreferenceManager.getPaymentBadge(mContext)==0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {

                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getPaymentEditedBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_navy)

                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)==0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
                else if (PreferenceManager.getPaymentBadge(mContext)>0 && PreferenceManager.getPaymentEditedBadge(mContext)>0)
                {
                    relImgNineDot.visibility= View.VISIBLE
                    relImgNineDot.setText(PreferenceManager.getPaymentBadge(mContext).toString())
                    relImgNineDot.setBackgroundResource(R.drawable.shape_circle_red)
                }
            }



        }

    }


    @Suppress("EqualsBetweenInconvertibleTypes")
    class DropListener : View.OnDragListener {
        override fun onDrag(v: View?, event: DragEvent?): Boolean {

            return true

        }

        private fun getButtonDrawablesAndText(touchedView: View, sPosition: Int) {
            if (sPosition != 0) {
                if (touchedView == relone) {
                    relImgone.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxtone.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonOneRegTabID(mContext, TAB_ID)
                    //setBackgroundColorForView(appController.listitemArrays[sPosition], relone)
                    setBackgroundColorForView(listitems[sPosition], relone)
                    PreferenceManager.setButtonOneRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == reltwo) {
                    relImgtwo.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxttwo.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonTwoRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], reltwo)
                    PreferenceManager.setButtonTwoRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == relthree) {
                    relImgthree.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxtthree.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonThreeRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relthree)
                    PreferenceManager.setButtonThreeRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == relfour) {
                    relImgfour.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxtfour.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonFourRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relfour)
                    PreferenceManager.setButtonFourRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == relfive) {
                    relImgfive.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxtfive.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonFiveRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relfive)
                    PreferenceManager.setButtonFiveRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == relsix) {
                    relImgsix.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    }  else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxtsix.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonSixRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relsix)
                    PreferenceManager.setButtonSixRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == relseven) {
                    relImgseven.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    } else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxtseven.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonSevenRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relseven)
                    PreferenceManager.setButtonSevenRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == releight) {
                    relImgeight.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    }else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    relTxteight.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonEightRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], releight)
                    PreferenceManager.setButtonEightRegTextImage(mContext, sPosition.toString())
                } else if (touchedView == relnine) {
                    relImgnine.setImageDrawable(mListImgArrays.getDrawable(sPosition))
                    val relstring: String
                    if (listitems[sPosition] == "CCAs") {
                        relstring = "CCAS"
                    }
                    else {
                        relstring = listitems[sPosition].toUpperCase(Locale.getDefault())
                    }
                    reltxtnine.text = relstring
                    getTabId(listitems[sPosition])
                    PreferenceManager.setButtonNineRegTabID(mContext, TAB_ID)
                    setBackgroundColorForView(listitems[sPosition], relnine)
                    PreferenceManager.setButtonNineRegTextImage(mContext, sPosition.toString())
                }

            }
        }

        private fun setBackgroundColorForView(s: String, v: View) {
            if (v == relone) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == reltwo) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relthree) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relfour) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relfive) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.rel_five))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relsix) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relseven) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == releight) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            } else if (v == relnine) {
                v.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent))
                saveButtonBgColor(v, ContextCompat.getColor(mContext, R.color.transparent))
            }
        }

        private fun saveButtonBgColor(v: View, color: Int) {

            if (v == relone) {
                PreferenceManager.setButtonOneGuestBg(mContext, color)
            } else if (v == reltwo) {
                PreferenceManager.setButtontwoGuestBg(mContext, color)
            } else if (v == relthree) {
                PreferenceManager.setButtonthreeGuestBg(mContext, color)
            } else if (v == relfour) {
                PreferenceManager.setButtonfourGuestBg(mContext, color)
            } else if (v == relfive) {
                PreferenceManager.setButtonfiveGuestBg(mContext, color)
            } else if (v == relsix) {
                PreferenceManager.setButtonsixGuestBg(mContext, color)
            } else if (v == relseven) {
                PreferenceManager.setButtonsevenGuestBg(mContext, color)
            } else if (v == releight) {
                PreferenceManager.setButtoneightGuestBg(mContext, color)
            } else if (v == relnine) {
                PreferenceManager.setButtonnineGuestBg(mContext, color)
            }
        }

        private fun getTabId(textdata: String) {

            when {

                textdata.equals(ClassNameConstants.CALENDAR) -> {
                    TAB_ID = NasTabConstants.TAB_CALENDAR_REG

                }
                textdata.equals(ClassNameConstants.NOTIFICATIONS) -> {
                    TAB_ID = NasTabConstants.TAB_NOTIFICATIONS_REG

                }

                textdata.equals(ClassNameConstants.COMMUNICATIONS) -> {
                    TAB_ID = NasTabConstants.TAB_COMMUNICATIONS_REG

                }
                textdata.equals(ClassNameConstants.PAYMENT) -> {
                    TAB_ID = NasTabConstants.TAB_TRIPS_REG
                }

                textdata.equals(ClassNameConstants.PARENT_ESSENTIALS) -> {
                    TAB_ID = NasTabConstants.TAB_PARENT_ESSENTIALS_REG
                }

                textdata.equals(ClassNameConstants.ABOUT_US) -> {
                    TAB_ID = NasTabConstants.TAB_ABOUT_US_REG
                }
                textdata.equals(ClassNameConstants.CONTACT_US) -> {
                    TAB_ID = NasTabConstants.TAB_CONTACT_US_REG
                }
                textdata.equals(ClassNameConstants.SETTINGS) -> {
                    TAB_ID = NasTabConstants.TAB_SETTINGS
                }
                textdata.equals(ClassNameConstants.LOGOUT) -> {
                    TAB_ID = NasTabConstants.TAB_LOGOUT_REG
                }

            }

        }

    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initializeUI() {
        pager = view!!.findViewById(R.id.bannerImagePager)
        relone = view!!.findViewById(R.id.relOne) as RelativeLayout
        reltwo = view!!.findViewById(R.id.relTwo) as RelativeLayout
        relthree = view!!.findViewById(R.id.relThree) as RelativeLayout
        relfour = view!!.findViewById(R.id.relFour) as RelativeLayout
        relfive = view!!.findViewById(R.id.relFive) as RelativeLayout
        relsix = view!!.findViewById(R.id.relSix) as RelativeLayout
        relseven = view!!.findViewById(R.id.relSeven) as RelativeLayout
        releight = view!!.findViewById(R.id.relEight) as RelativeLayout
        relnine = view!!.findViewById(R.id.relNine) as RelativeLayout

        relTxtone = view!!.findViewById(R.id.relTxtOne) as TextView
        relTxttwo = view!!.findViewById(R.id.relTxtTwo) as TextView
        relTxtthree = view!!.findViewById(R.id.relTxtThree) as TextView
        relTxtfour = view!!.findViewById(R.id.relTxtFour) as TextView
        relTxtfive = view!!.findViewById(R.id.relTxtFive) as TextView
        relTxtsix = view!!.findViewById(R.id.relTxtSix) as TextView
        relTxtseven = view!!.findViewById(R.id.relTxtSeven) as TextView
        relTxteight = view!!.findViewById(R.id.relTxtEight) as TextView
        reltxtnine = view!!.findViewById(R.id.relTxtNine) as TextView


        relImgOneDot = view!!.findViewById(R.id.relImgOneDot) as TextView
        relImgTwoDot = view!!.findViewById(R.id.relImgTwoDot) as TextView
        relImgThreeDot = view!!.findViewById(R.id.relImgThreeDot) as TextView
        relImgFourDot = view!!.findViewById(R.id.relImgFourDot) as TextView
        relImgFiveDot = view!!.findViewById(R.id.relImgFiveDot) as TextView
        relImgSixDot = view!!.findViewById(R.id.relImgSixDot) as TextView
        relImgSevenDot = view!!.findViewById(R.id.relImgSevenDot) as TextView
        relImgEightDot = view!!.findViewById(R.id.relImgEightDot) as TextView
        relImgNineDot = view!!.findViewById(R.id.relImgNineDot) as TextView

        relImgone = view!!.findViewById(R.id.relImgOne) as ImageView
        relImgtwo = view!!.findViewById(R.id.relImgTwo) as ImageView
        relImgthree = view!!.findViewById(R.id.relImgThree) as ImageView
        relImgfour = view!!.findViewById(R.id.relImgFour) as ImageView
        relImgfive = view!!.findViewById(R.id.relImgFive) as ImageView
        relImgsix = view!!.findViewById(R.id.relImgSix) as ImageView
        relImgseven = view!!.findViewById(R.id.relImgSeven) as ImageView
        relImgeight = view!!.findViewById(R.id.relImgEight) as ImageView
        relImgnine = view!!.findViewById(R.id.relImgNine) as ImageView
        mSectionText = arrayOfNulls(9)



    }




    private fun CHECKINTENTVALUE(intentTabId: String) {
        TAB_ID = intentTabId
        var mFragment: Fragment? = null
        Log.e("Click",intentTabId)
        Log.e("tabID",TAB_ID.toString())
        if (PreferenceManager.getUserCode(mContext).equals(""))
        {
            when (intentTabId)
            {

                NasTabConstants.TAB_CALENDAR_REG -> {
                   CommonMethods.showDialogueWithOk(mContext,"This Feature is only available for registered users","Alert")
                }
                NasTabConstants.TAB_COMMUNICATIONS_REG -> {
                    CommonMethods.showDialogueWithOk(mContext,"This Feature is only available for registered users","Alert")
                }
                NasTabConstants.TAB_NOTIFICATIONS_REG -> {
                    CommonMethods.showDialogueWithOk(mContext,"This Feature is only available for registered users","Alert")
                }
                NasTabConstants.TAB_TRIPS_REG -> {
                    CommonMethods.showDialogueWithOk(mContext,"This Feature is only available for registered users","Alert")
                }
                NasTabConstants.TAB_PARENT_ESSENTIALS_REG -> {
                    CommonMethods.showDialogueWithOk(mContext,"This Feature is only available for registered users","Alert")
                }
                NasTabConstants.TAB_ABOUT_US_REG -> {
                    CommonMethods.showDialogueWithOk(mContext,"This Feature is only available for registered users","Alert")
                }

                NasTabConstants.TAB_CONTACT_US_REG -> {
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        checkpermission()


                    } else {
                        mFragment = ContactUsFragment()
                        fragmentIntent(mFragment)
                    }
                }
                NasTabConstants.TAB_SETTINGS -> {
                    mFragment = SettingsFragment()
                    fragmentIntent(mFragment)
                }
                NasTabConstants.TAB_LOGOUT_REG -> {
//                mFragment = NotificationFragment()
//                fragmentIntent(mFragment)
                    showLogoutDialogue(mContext)
                }
            }
        }
        else{
            when (intentTabId)
            {


                NasTabConstants.TAB_CALENDAR_REG -> {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.WRITE_CALENDAR
                        ) != PackageManager.PERMISSION_GRANTED) {
                        checkcalpermission()


                    } else {
                        mFragment = CalendarFragment()
                        fragmentIntent(mFragment)
                    }

                }
                NasTabConstants.TAB_COMMUNICATIONS_REG -> {
                    mFragment = CommunicationFragment()
                    fragmentIntent(mFragment)
                }
                NasTabConstants.TAB_NOTIFICATIONS_REG -> {
                    mFragment = NotificationFragment()
                    fragmentIntent(mFragment)
                }
                NasTabConstants.TAB_TRIPS_REG -> {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) != PackageManager.PERMISSION_GRANTED) {
                        checkpaypermission()


                    } else {
                        mFragment = PaymentFragment()
                        fragmentIntent(mFragment)
                    }

                }
                NasTabConstants.TAB_PARENT_ESSENTIALS_REG -> {
                    mFragment = ParentsEssentialsFragment()
                    fragmentIntent(mFragment)
                }
                NasTabConstants.TAB_ABOUT_US_REG -> {
                    mFragment = NordAngliaEductaionFragment()
                    fragmentIntent(mFragment)
                }

                NasTabConstants.TAB_CONTACT_US_REG -> {
                    if (ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            mContext,
                            Manifest.permission.CALL_PHONE
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        checkpermission()


                    } else {
                        mFragment = ContactUsFragment()
                        fragmentIntent(mFragment)
                    }
                }
                NasTabConstants.TAB_SETTINGS -> {
                    mFragment = SettingsFragment()
                    fragmentIntent(mFragment)
                }
                NasTabConstants.TAB_LOGOUT_REG -> {
//                mFragment = NotificationFragment()
//                fragmentIntent(mFragment)
                    showLogoutDialogue(mContext)
                }
            }
        }

    }



    private fun checkpermission() {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE
                ),
                123
            )
        }
    }
    private fun checkcalpermission() {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.READ_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.WRITE_CALENDAR
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(
                    Manifest.permission.WRITE_CALENDAR,
                    Manifest.permission.READ_CALENDAR
                ),
                123
            )
        }
    }
    private fun checkpaypermission() {
        if (ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this.activity!!,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                123
            )
        }
    }
    @SuppressLint("UseRequireInsteadOfGet")
    fun fragmentIntent(mFragment: Fragment?)
    {
        if (mFragment != null) {
            val fragmentManager = activity!!.supportFragmentManager
            fragmentManager.beginTransaction()
                .add(R.id.fragment_holder, mFragment, AppController.mTitles)
                .addToBackStack(AppController.mTitles).commitAllowingStateLoss() //commit
            //.commit()
        }
    }


    fun getBadgeApi() {

        val call: Call<HomeBadgeResponse> = ApiClient.getClient.homebadge("Bearer "+PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<HomeBadgeResponse> {
            override fun onFailure(call: Call<HomeBadgeResponse>, t: Throwable) {
            }

            override fun onResponse(call: Call<HomeBadgeResponse>, response: Response<HomeBadgeResponse>) {
                val responsedata = response.body()
                if (responsedata != null) {
                    try {
                        if (response.body()!!.status==100)
                        {
                            val calendar_badge=response.body()!!.data.calendar_badge
                            val calendar_edited_badge=response.body()!!.data.calendar_edited_badge
                            val notification_badge=response.body()!!.data.notification_badge
                            val notification_edited_badge=response.body()!!.data.notification_edited_badge
                            val paymentitem_badge=response.body()!!.data.paymentitem_badge
                            val paymentitem_edit_badge=response.body()!!.data.paymentitem_edit_badge
                            PreferenceManager.setCalendarBadge(mContext,calendar_badge)
                            PreferenceManager.setCalendarEditedBadge(mContext,calendar_edited_badge)
                            PreferenceManager.setNotificationBadge(mContext,notification_badge)
                            PreferenceManager.setNotificationEditedBadge(mContext,notification_edited_badge)
                            PreferenceManager.setPaymentBadge(mContext,paymentitem_badge)
                            PreferenceManager.setPaymentEditedBadge(mContext,paymentitem_edit_badge)

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    fun showLogoutDialogue(context: Context)
    {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_logout)
        var btn_Ok = dialog.findViewById(R.id.btn_Ok) as Button
        var btn_Cancel = dialog.findViewById(R.id.btn_Cancel) as Button
        var progress = dialog.findViewById(R.id.progressDialog) as ProgressBar
        progress.visibility=View.GONE
        btn_Ok.setOnClickListener()
        {
            if (PreferenceManager.getUserCode(context).equals(""))
            {
                dialog.dismiss()
                PreferenceManager.setUserCode(context,"")
                PreferenceManager.setUserEmail(context,"")
                val mIntent = Intent(activity, LoginActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                mContext.startActivity(mIntent)
            }
            else{
                progress.visibility=View.VISIBLE
                callLogoutApi(context,dialog,progress)
            }


        }
        btn_Cancel.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
        })
        dialog.show()
    }

    private fun callLogoutApi(context: Context,dialog: Dialog,progessbar:ProgressBar) {
        progessbar.visibility = View.VISIBLE
        val call: Call<LogoutResponseModel> = ApiClient.getClient.logout("Bearer "+ PreferenceManager.getUserCode(context))
        call.enqueue(object : Callback<LogoutResponseModel> {
            override fun onFailure(call: Call<LogoutResponseModel>, t: Throwable) {
                progessbar.visibility = View.GONE

            }

            override fun onResponse(
                call: Call<LogoutResponseModel>,
                response: Response<LogoutResponseModel>
            ) {
                progessbar.visibility = View.GONE

                if (response.body()!!.status == 100) {

                    dialog.dismiss()
                    PreferenceManager.setUserCode(context,"")
                    PreferenceManager.setUserEmail(context,"")
                    val mIntent = Intent(activity, LoginActivity::class.java)
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    mContext.startActivity(mIntent)

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
