package com.nas.naisak.fragment.calendar

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.fragment.calendar.adapter.CalendarListAdapter
import com.nas.naisak.fragment.calendar.adapter.CustomSpinnerAdapter
import com.nas.naisak.fragment.calendar.model.CalendarArrayModel
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe
import com.nas.naisak.fragment.calendar.model.CalendarDetailsModelUse
import com.nas.naisak.fragment.calendar.model.CalendarResponseModel
import kotlinx.android.synthetic.main.fragment_calendar.*
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
    var monthValues: ArrayList<String> = ArrayList()
    var dayValues: ArrayList<String> = ArrayList()
    var yearValues: ArrayList<String> = ArrayList()
    lateinit var mListView: RecyclerView
    lateinit var monthListView: ListView
    lateinit var dayListView: ListView
    lateinit var yearListView: ListView
    lateinit var linearLayoutManager: LinearLayoutManager

    lateinit var mTermCalendar:TextView
    lateinit var daySpinner:TextView
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
        monthListView=view?.findViewById(R.id.monthListView) as ListView
        dayListView=view?.findViewById(R.id.dayListView) as ListView
        yearListView=view?.findViewById(R.id.yearListView) as ListView
        mTermCalendar=view?.findViewById(R.id.moreImage) as TextView
        daySpinner=view?.findViewById(R.id.daySpinner) as TextView
        linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)

        mTermCalendar.setOnClickListener(View.OnClickListener {

            val intent = Intent(activity, TermCalendarActivity::class.java)
            activity?.startActivity(intent)
        })
        populateMonthSpinner()
        populateYearSpinner()
        populateDaySpinner()

        dayListView.setOnItemClickListener{parent, view, position, id ->

        }
        monthListView.setOnItemClickListener{parent, view, position, id ->

        }
        dayListView.setOnItemClickListener{parent, view, position, id ->

        }
    }
    private fun populateMonthSpinner()
    {
        val months = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
        monthValues = ArrayList()
        for (i in 0.. months.size-1)
        {
            val cal = Calendar.getInstance()
            val month_date = SimpleDateFormat("MMMM", Locale.ENGLISH)
            cal[Calendar.DATE] = 5
            cal[Calendar.MONTH] = months[i]
            var month_name = month_date.format(cal.time)
            if (month_name.equals("January"))
            {
                month_name= "JAN"
            }
            else  if (month_name.equals("February"))
            {
                month_name= "FEB"
            }
            else  if (month_name.equals("March"))
            {
                month_name= "MAR"
            }
            else  if (month_name.equals("April"))
            {
                month_name= "APR"
            }
            else  if (month_name.equals("May"))
            {
                month_name= "MAY"
            }
            else  if (month_name.equals("June"))
            {
                month_name= "JUN"
            }
            else  if (month_name.equals("July"))
            {
                month_name= "JUL"
            }
            else  if (month_name.equals("August"))
            {
                month_name= "AUG"
            }
            else  if (month_name.equals("September"))
            {
                month_name= "SEP"
            }
            else  if (month_name.equals("October"))
            {
                month_name= "OCT"
            }
            else  if (month_name.equals("November"))
            {
                month_name= "NOV"
            }
            else  if (month_name.equals("December"))
            {
                month_name= "DEC"
            }
            monthValues.add(month_name)
        }

       var monthdataAdapter = CustomSpinnerAdapter(
           mContext,
           R.layout.spinner_textview_item,
           monthValues,
           -1
       )
        monthListView.adapter = monthdataAdapter
    }
    private fun populateYearSpinner()
    {
        val yearInt = Calendar.getInstance()[Calendar.YEAR] - 1
        var yearInt1=yearInt+1
        var yearInt2=yearInt+2
        var yearInt3=yearInt+3
        var yearInt4=yearInt+4
        var yearInt5=yearInt+5
        yearValues.add(yearInt.toString() + "")
        yearValues.add(yearInt1.toString() + "")
        yearValues.add(yearInt2.toString() + "")
        yearValues.add(yearInt3.toString() + "")
        yearValues.add(yearInt4.toString() + "")
        yearValues.add(yearInt5.toString() + "")
        var yearDataAdapter = CustomSpinnerAdapter(
            mContext,
            R.layout.spinner_textview_item,
            monthValues,
            -1
        )
        yearListView.adapter = yearDataAdapter
    }
    private fun populateDaySpinner()
    {
        val selctedCalender = Calendar.getInstance()
        val noOfDays = selctedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)
        Log.e("noofdays", noOfDays.toString() + "")
        dayValues = ArrayList()
        when (noOfDays) {
            28 -> dayValues =
                java.util.ArrayList(Arrays.asList(*mContext.resources.getStringArray(R.array.month28)))
            29 -> dayValues =
                java.util.ArrayList(Arrays.asList(*mContext.resources.getStringArray(R.array.month29)))
            30 -> dayValues =
                java.util.ArrayList(Arrays.asList(*mContext.resources.getStringArray(R.array.month30)))
            31 -> dayValues =
                java.util.ArrayList(Arrays.asList(*mContext.resources.getStringArray(R.array.month31)))
            else -> {
            }
        }
        var dayAdapter = CustomSpinnerAdapter(
            mContext,
            R.layout.spinner_textview_item,
            monthValues,
            -1
        )
        dayListView.adapter = dayAdapter

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
                            eventModel = ArrayList()
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
                                    xModel.toTime =
                                        calendarDetailArrayList.get(i).details.get(j).endtime
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
                                    if (!calendarDetailArrayList.get(i).details.get(j).starttime.equals(
                                            ""
                                        )
                                    ) {
                                        val dateStart = format1.parse(
                                            calendarDetailArrayList.get(i).details.get(
                                                j
                                            ).starttime
                                        )
                                        val startTime = format2.format(dateStart)
                                        xModel.starttime = startTime

                                    } else {
                                        xModel.starttime = ""
                                    }
                                    if (!calendarDetailArrayList.get(i).details.get(j).endtime.equals(
                                            ""
                                        )
                                    ) {
                                        val dateStart = format1.parse(
                                            calendarDetailArrayList.get(i).details.get(
                                                j
                                            ).endtime
                                        )
                                        val endtime = format2.format(dateStart)
                                        xModel.endtime = endtime
                                    } else {

                                        xModel.endtime = ""
                                    }

                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                                xModel.title = calendarDetailArrayList.get(i).details.get(j).title
                                xModel.vpml = calendarDetailArrayList.get(i).details.get(j).vpml
                                eventModel.add(xModel)
                            }
                            model.details = eventModel
                            calendarDetailArrayListUse.add(model)

                        }
                        Log.e("Adapter Line", "Work1")
                        val calendarAdapter = CalendarListAdapter(
                            mContext,
                            calendarDetailArrayListUse
                        )
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