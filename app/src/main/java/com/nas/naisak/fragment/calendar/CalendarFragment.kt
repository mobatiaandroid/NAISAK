package com.nas.naisak.fragment.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.fragment.calendar.adapter.CalendarListAdapter
import com.nas.naisak.fragment.calendar.model.CalendarArrayModel
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe
import com.nas.naisak.fragment.calendar.model.CalendarDetailsModelUse
import com.nas.naisak.fragment.calendar.model.CalendarResponseModel
import com.nas.naisak.fragment.communications.adapter.CommunicationAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment  : Fragment() {
    lateinit var mContext: Context
    lateinit var calendarDetailArrayList:ArrayList<CalendarArrayModel>
    lateinit var calendarDetailArrayListUse:ArrayList<CalendarArrayModelUSe>
    lateinit var eventModel:ArrayList<CalendarDetailsModelUse>
    lateinit var mListView: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialiseUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            callCalendarDetail()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initialiseUI() {
        mContext = requireContext()
        mListView=view?.findViewById(R.id.calList) as RecyclerView
        linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)


    }

    private fun callCalendarDetail() {
        calendarDetailArrayList=ArrayList()
        calendarDetailArrayListUse=ArrayList()
        val call: Call<CalendarResponseModel> = ApiClient.getClient.calendar(
            "Bearer " + PreferenceManager.getUserCode(
                mContext
            )
        )
        call.enqueue(object : Callback<CalendarResponseModel> {
            override fun onFailure(call: Call<CalendarResponseModel>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<CalendarResponseModel>,
                response: Response<CalendarResponseModel>
            ) {

                if (response.body()!!.status == 100) {

                    calendarDetailArrayList.addAll(response.body()!!.data.calendarArray)
                    if (calendarDetailArrayList.size > 0) {
                        for (i in 0..calendarDetailArrayList.size - 1) {
                            var model = CalendarArrayModelUSe()
                            model.date = calendarDetailArrayList.get(i).date
                            eventModel= ArrayList()
                            val str = calendarDetailArrayList.get(i).date
                            val splitStr =
                                str.trim { it <= ' ' }.split("\\s+".toRegex()).toTypedArray()
                            Log.e("SPLITDATA", splitStr[0])
                            for (j in 0..calendarDetailArrayList.get(i).details.size - 1) {
                                var xModel = CalendarDetailsModelUse()
                                xModel.dayStringDate = splitStr[0]
                                xModel.dayDate = splitStr[1]
                                xModel.monthDate = splitStr[2]
                                xModel.yearDate = splitStr[3]
                                xModel.id = calendarDetailArrayList.get(i).details.get(j).id
                                xModel.status = calendarDetailArrayList.get(i).details.get(j).status
                                if (calendarDetailArrayList.get(i).details.get(j).starttime.equals("")) {
                                    xModel.fromTime = ""
                                } else {
                                    xModel.fromTime =
                                        calendarDetailArrayList.get(i).details.get(j).starttime
                                }
                                if (calendarDetailArrayList.get(i).details.get(j).endtime.equals("")) {
                                    xModel.toTime = ""
                                } else {
                                    xModel.toTime = calendarDetailArrayList.get(i).details.get(j).endtime
                                }
                                xModel.eventAddToCalendar = false
                                xModel.isAllday =
                                    calendarDetailArrayList.get(i).details.get(j).isAllday
                                val format1: DateFormat = SimpleDateFormat(
                                    "HH:mm:ss",
                                    Locale.ENGLISH
                                )
                                try {
                                    val format2 = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                                    if (!calendarDetailArrayList.get(i).details.get(j).starttime.equals(""))
                                    {
                                        val dateStart = format1.parse(calendarDetailArrayList.get(i).details.get(j).starttime)
                                        val startTime = format2.format(dateStart)
                                        xModel.starttime=startTime

                                    } else {
                                        xModel.starttime=""
                                    }
                                    if (!calendarDetailArrayList.get(i).details.get(j).endtime.equals(""))
                                    {
                                        val dateStart = format1.parse(calendarDetailArrayList.get(i).details.get(j).endtime)
                                        val endtime = format2.format(dateStart)
                                        xModel.endtime=endtime
                                    }
                                    else {

                                        xModel.endtime=""
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                xModel.title = calendarDetailArrayList.get(i).details.get(j).title
                                xModel.vpml = calendarDetailArrayList.get(i).details.get(j).vpml
                                eventModel.add(xModel)
                            }
                            model.details=eventModel
                            calendarDetailArrayListUse.add(model)

                        }
                        Log.e("Adapter Line","Work1")
                        val calendarAdapter = CalendarListAdapter(mContext,calendarDetailArrayListUse)
                        mListView.adapter = calendarAdapter
                    }
                } else {
                    if (response.body()!!.status == 101) {
                        CommonMethods.showDialogueWithOk(mContext, "Some error occured", "Alert")
                    }
                }

            }

        })
    }

}