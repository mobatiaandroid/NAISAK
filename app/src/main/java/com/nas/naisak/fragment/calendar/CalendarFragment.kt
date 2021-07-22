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
import android.widget.ImageView
import android.widget.ListView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activtiy.home.HomeActivity
import com.nas.naisak.activtiy.login.LoginActivity
import com.nas.naisak.activtiy.tutorial.TutorialActivity
import com.nas.naisak.constants.ApiClient
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.fragment.calendar.adapter.CalendarListAdapter
import com.nas.naisak.fragment.calendar.adapter.CustomSpinnerAdapter
import com.nas.naisak.fragment.calendar.model.CalendarArrayModel
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe
import com.nas.naisak.fragment.calendar.model.CalendarDetailsModelUse
import com.nas.naisak.fragment.calendar.model.CalendarResponseModel
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
    var tempArrayList=ArrayList<CalendarArrayModelUSe>()
    lateinit var eventModel:ArrayList<CalendarDetailsModelUse>
    var monthValues: ArrayList<String> = ArrayList()
    var dayValues: ArrayList<String> = ArrayList()
    var yearValues: ArrayList<String> = ArrayList()
    lateinit var mListView: RecyclerView
    lateinit var monthListView: ListView
    lateinit var dayListView: ListView
    lateinit var yearListView: ListView
    lateinit var commonRelList: RelativeLayout
    lateinit var progressDialog: RelativeLayout
    lateinit var linearLayoutManager: LinearLayoutManager
    var monthSpinSelect:Boolean=true
    var yearSpinSelect:Boolean=true
    var daySpinSelect:Boolean=true
    var isSelectedSpinner:Boolean=false
    var dayPosition:Int=-1
    var selectedMonthId:Int=-1
    var mPosYear:Int=0
    var mPosMonth:Int=0
    var selectedMonth:String=""
    var selectedYear:String=""
    lateinit var mTermCalendar:TextView
    lateinit var daySpinner:TextView
    lateinit var yearSpinner:TextView
    lateinit var monthSpinner:TextView
    lateinit var mAddAllEvents:ImageView
    lateinit var infoImg:ImageView
    lateinit var clearData:ImageView
    var isClicked:Boolean=false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = requireContext()
        if(!PreferenceManager.getIsCalendarFirstLaunch(mContext))
        {
            PreferenceManager.isCalendarFirstLaunch(mContext,true)
            val intent = Intent(activity, CalendarTutorialActivity::class.java)
            activity?.startActivity(intent)
        }
        initialiseUI()
        if (CommonMethods.isInternetAvailable(mContext)) {
            isClicked=true
            callCalendarDetail()
        } else {
            CommonMethods.showSuccessInternetAlert(mContext)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initialiseUI() {

        isSelectedSpinner=false
        mListView=view?.findViewById(R.id.calList) as RecyclerView
        monthListView=view?.findViewById(R.id.monthListView) as ListView
        dayListView=view?.findViewById(R.id.dayListView) as ListView
        yearListView=view?.findViewById(R.id.yearListView) as ListView
        mTermCalendar=view?.findViewById(R.id.moreImage) as TextView
        daySpinner=view?.findViewById(R.id.daySpinner) as TextView
        yearSpinner=view?.findViewById(R.id.yearSpinner) as TextView
        monthSpinner=view?.findViewById(R.id.monthSpinner) as TextView
        mAddAllEvents=view?.findViewById(R.id.addAllBtn) as ImageView
        infoImg=view?.findViewById(R.id.infoImg) as ImageView
        clearData=view?.findViewById(R.id.clearData) as ImageView
        commonRelList=view?.findViewById(R.id.commonRelList) as RelativeLayout
        progressDialog=view?.findViewById(R.id.progressDialog) as RelativeLayout
        val aniRotate: Animation =
            AnimationUtils.loadAnimation(mContext, R.anim.linear_interpolator)
        progressDialog.startAnimation(aniRotate)
        progressDialog.visibility=View.VISIBLE
        linearLayoutManager = LinearLayoutManager(mContext)
        mListView.layoutManager = linearLayoutManager
        selectedMonth="MONTH"
        selectedYear="YEAR"

        mTermCalendar.setOnClickListener(View.OnClickListener {

            if (!isClicked)
            {
                val intent = Intent(activity, TermCalendarActivity::class.java)
                activity?.startActivity(intent)
            }

        })
        populateMonthSpinner()
        populateYearSpinner()
        populateDaySpinner()
        clearData.setOnClickListener(View.OnClickListener {
            if (!isClicked)
            {
                commonRelList.visibility=View.GONE
                dayListView.visibility=View.GONE
                monthListView.visibility=View.GONE
                yearListView.visibility=View.GONE
                daySpinner.setText("DAY")
                monthSpinner.setText("MONTH")
                yearSpinner.setText("YEAR")
                isSelectedSpinner=false
                selectedMonth="MONTH"
                selectedYear="YEAR"
                tempArrayList.clear()
                mListView.visibility=View.VISIBLE
                val calendarAdapter = CalendarListAdapter(mContext, calendarDetailArrayListUse)
                calendarAdapter.notifyDataSetChanged()
                mListView.adapter = calendarAdapter
            }


        })

        daySpinner.setOnClickListener(View.OnClickListener {

            if (!isClicked)
            {
                if (daySpinSelect)
                {
                    commonRelList.visibility=View.VISIBLE
                    dayListView.visibility=View.VISIBLE
                    monthListView.visibility=View.GONE
                    yearListView.visibility=View.GONE
                    daySpinSelect=false
                    populateDaySpinner()

                }
                else{
                    daySpinSelect=true
                    commonRelList.visibility=View.GONE
                    dayListView.visibility=View.GONE
                }
            }

        })
        monthSpinner.setOnClickListener(View.OnClickListener {

            if(!isClicked)
            {
                if (monthSpinSelect)
                {
                    commonRelList.visibility=View.VISIBLE
                    monthListView.visibility=View.VISIBLE
                    dayListView.visibility=View.GONE
                    yearListView.visibility=View.GONE
                    monthSpinSelect=false
                    populateMonthSpinner()

                }
                else{
                    monthSpinSelect=true
                    commonRelList.visibility=View.GONE
                    monthListView.visibility=View.GONE
                }
            }

        })

        yearSpinner.setOnClickListener(View.OnClickListener {
            if(!isClicked)
            {
                if (yearSpinSelect)
                {
                    commonRelList.visibility=View.VISIBLE
                    yearListView.visibility=View.VISIBLE
                    dayListView.visibility=View.GONE
                    monthListView.visibility=View.GONE
                    yearSpinSelect=false
                    populateYearSpinner()

                }
                else{
                    yearSpinSelect=true
                    commonRelList.visibility=View.GONE
                    yearListView.visibility=View.GONE
                }
            }

        })
        infoImg.setOnClickListener(View.OnClickListener {
            if (!isClicked)
            {
                val intent = Intent(activity, CalendarTutorialActivity::class.java)
                activity?.startActivity(intent)
            }

        })
        mAddAllEvents.setOnClickListener(View.OnClickListener {
            var mEventAdded:Boolean=false
            if(calendarDetailArrayListUse.size>0)
            {
               if (isSelectedSpinner)
               {
                 if (tempArrayList.size>0)
                 {
                    for (k in 0.. tempArrayList.size-1)
                    {
                        for (position in 0..tempArrayList.get(k).details.size-1)
                        {

                        }
                    }
                 }
               }
            }


        })
        dayListView.setOnItemClickListener{ parent, view, position, id ->
            daySpinSelect=true
            commonRelList.visibility=View.GONE
            dayListView.visibility=View.GONE
            dayPosition=position
            isSelectedSpinner=true
            daySpinner.setText(dayValues.get(position).toString())
            if (!daySpinner.text.toString().equals("DAY") && !monthSpinner.text.toString().equals("MONTH") && !yearSpinner.text.toString().equals("YEAR"))
            {
                Log.e("CAL DAY","Con:1")
                tempArrayList= ArrayList()
                if (calendarDetailArrayListUse.size>0)
                {
                    for (i in 0.. calendarDetailArrayListUse.size-1)
                    {
                        if (calendarDetailArrayListUse.get(i).dayDate.equals(daySpinner.text.toString()) || calendarDetailArrayListUse.get(i).dayDate.equals("0" + daySpinner.text.toString()) && calendarDetailArrayListUse.get(i
                            ).yearDate.equals(yearSpinner.text.toString()) && calendarDetailArrayListUse.get(
                                i
                            ).monthDate.toLowerCase().contains(
                                monthSpinner.text.toString().toLowerCase()
                            ))
                        {
                            tempArrayList.add(calendarDetailArrayListUse.get(i))
                        }
                    }
                    if (tempArrayList.size>0)
                    {
                        mListView.visibility=View.VISIBLE
                        val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                        calendarAdapter.notifyDataSetChanged()
                        mListView.adapter = calendarAdapter
                    }
                    else
                    {
                        mListView.visibility=View.INVISIBLE
                    }


                }

            }
            else if(!monthSpinner.text.toString().equals("MONTH") && !yearSpinner.text.toString().equals("YEAR"))
            {
                Log.e("CAL DAY","Con:2")
                tempArrayList= ArrayList()
                if (calendarDetailArrayListUse.size>0)
                {
                    for (i in 0.. calendarDetailArrayListUse.size-1)
                    {
                        if ( calendarDetailArrayListUse.get(i).yearDate.equals(yearSpinner.text.toString()) && calendarDetailArrayListUse.get(
                                i
                            ).monthDate.toLowerCase().contains(
                                monthSpinner.text.toString().toLowerCase()
                            ))
                        {
                            tempArrayList.add(calendarDetailArrayListUse.get(i))
                        }
                    }
                    if (tempArrayList.size>0)
                    {
                        mListView.visibility=View.VISIBLE
                        val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                        calendarAdapter.notifyDataSetChanged()
                        mListView.adapter = calendarAdapter
                    }
                    else{
                        mListView.visibility=View.INVISIBLE
                    }


                }
            }
            else  if (!daySpinner.text.toString().equals("DAY") && !monthSpinner.text.toString().equals("MONTH"))
            {
                Log.e("CAL DAY","Con:3")
                tempArrayList= ArrayList()
                if (calendarDetailArrayListUse.size>0)
                {
                    for (i in 0.. calendarDetailArrayListUse.size-1)
                    {
                        if (calendarDetailArrayListUse.get(i).dayDate.equals(daySpinner.text.toString()) || calendarDetailArrayListUse.get(
                                i
                            ).dayDate.equals("0" + daySpinner.text.toString()) && calendarDetailArrayListUse.get(
                                i
                            ).monthDate.toLowerCase().contains(
                                monthSpinner.text.toString().toLowerCase()
                            ))
                        {
                            tempArrayList.add(calendarDetailArrayListUse.get(i))
                        }
                    }
                    if (tempArrayList.size>0)
                    {
                        mListView.visibility=View.VISIBLE
                        val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                        calendarAdapter.notifyDataSetChanged()
                        mListView.adapter = calendarAdapter
                    }
                    else
                    {
                        mListView.visibility=View.INVISIBLE
                    }


                }

            }
            else  if ( !monthSpinner.text.toString().equals("MONTH"))
            {
                Log.e("CAL DAY","Con:4")
                tempArrayList= ArrayList()
                if (calendarDetailArrayListUse.size>0)
                {
                    for (i in 0.. calendarDetailArrayListUse.size-1)
                    {
                        if (calendarDetailArrayListUse.get(i).monthDate.toLowerCase().contains(
                                monthSpinner.text.toString().toLowerCase()
                            ))
                        {
                            tempArrayList.add(calendarDetailArrayListUse.get(i))
                        }
                    }
                    if (tempArrayList.size>0)
                    {
                        mListView.visibility=View.VISIBLE
                        val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                        calendarAdapter.notifyDataSetChanged()
                        mListView.adapter = calendarAdapter
                    }
                    else{
                        mListView.visibility=View.INVISIBLE
                    }


                }

            }
            else  if (!daySpinner.text.toString().equals("DAY"))
            {
                Log.e("CAL DAY","Con:5")
                tempArrayList= ArrayList()
                if (calendarDetailArrayListUse.size>0)
                {
                    Log.e("CAL DAY","Con:51")
                    for (i in 0.. calendarDetailArrayListUse.size-1)
                    {
                        if (calendarDetailArrayListUse.get(i).dayDate.equals(daySpinner.text.toString()) || calendarDetailArrayListUse.get(i).dayDate.equals("0" + daySpinner.text.toString()))
                        {
                            Log.e("CAL DAY","Con:52")
                            tempArrayList.add(calendarDetailArrayListUse.get(i))
                        }
                    }
                    if (tempArrayList.size>0)
                    {
                        mListView.visibility=View.VISIBLE
                        Log.e("CAL DAY","Con:53")
                        val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                        calendarAdapter.notifyDataSetChanged()
                        mListView.adapter = calendarAdapter
                    }
                    else
                    {
                        mListView.visibility=View.INVISIBLE
                    }


                }

            }
            else  if ( !yearSpinner.text.toString().equals("YEAR"))
            {
                Log.e("CAL DAY","Con:6")
                tempArrayList= ArrayList()
                if (calendarDetailArrayListUse.size>0)
                {
                    for (i in 0.. calendarDetailArrayListUse.size-1)
                    {
                        if (calendarDetailArrayListUse.get(i).yearDate.equals(yearSpinner.text.toString()))
                        {
                            tempArrayList.add(calendarDetailArrayListUse.get(i))
                        }
                    }
                    if (tempArrayList.size>0)
                    {
                        mListView.visibility=View.VISIBLE
                        val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                        calendarAdapter.notifyDataSetChanged()
                        mListView.adapter = calendarAdapter
                    }
                    else
                    {
                        mListView.visibility=View.INVISIBLE
                    }


                }

            }

        }
        monthListView.setOnItemClickListener{ parent, view, position, id ->
            monthSpinSelect=true
            isSelectedSpinner=true
            commonRelList.visibility=View.GONE
            monthListView.visibility=View.GONE
            selectedMonth=monthValues.get(position).toString()
            monthSpinner.setText(monthValues.get(position).toString())
            mPosMonth=position
            if (selectedMonth.equals("JAN"))
            {
                selectedMonthId=1
            }
            else if (selectedMonth.equals("FEB"))
            {
                selectedMonthId=2
            }
            else if (selectedMonth.equals("MAR"))
            {
                selectedMonthId=3
            }
            else if (selectedMonth.equals("APR"))
            {
                selectedMonthId=4
            }
            else if (selectedMonth.equals("MAY"))
            {
                selectedMonthId=5
            }
            else if (selectedMonth.equals("JUN"))
            {
                selectedMonthId=6
            }
            else if (selectedMonth.equals("JUL"))
            {
                selectedMonthId=7
            }
            else if (selectedMonth.equals("AUG"))
            {
                selectedMonthId=8
            }
            else if (selectedMonth.equals("SEP"))
            {
                selectedMonthId=9
            }
            else if (selectedMonth.equals("OCT"))
            {
                selectedMonthId=10
            }
            else if (selectedMonth.equals("NOV"))
            {
                selectedMonthId=10
            }
            else if (selectedMonth.equals("DEC"))
            {
                selectedMonthId=10
            }
            selectedYear=yearValues.get(mPosYear).toString()
            if (!selectedMonth.equals("MONTH") && !selectedYear.equals("YEAR"))
            {
               if (selectedYear.equals("YEAR"))
               {
                   selectedYear=yearValues.get(0).toString()
               }
                populateDaySpinner(selectedMonthId - 1, Integer.valueOf(selectedYear))
            }
            else if (!selectedMonth.equals("MONTH"))
            {
                populateDaySpinner(selectedMonthId - 1, Calendar.YEAR - 1)
            }
            else if (!selectedMonth.equals("YEAR"))
            {
                populateDaySpinner(Calendar.MONTH, Integer.valueOf(selectedYear))
            }else
            {
                populateDaySpinner(Calendar.MONTH, Calendar.YEAR - 1)
            }



        }
        yearListView.setOnItemClickListener{ parent, view, position, id ->
            yearSpinSelect=true
            isSelectedSpinner=true
            commonRelList.visibility=View.GONE
            yearListView.visibility=View.GONE
            mPosYear=position
            yearSpinner.setText(yearValues.get(position).toString())
            selectedYear=yearValues.get(position).toString()
            if (!selectedMonth.equals("MONTH") && !selectedYear.equals("YEAR"))
            {
                if (selectedYear.equals("YEAR"))
                {
                    selectedYear=yearValues.get(0).toString()
                }
                populateDaySpinner(selectedMonthId - 1, Integer.valueOf(selectedYear))
            }
            else if (!selectedMonth.equals("MONTH"))
            {
                populateDaySpinner(selectedMonthId - 1, Calendar.YEAR - 1)
            }
            else if (!selectedMonth.equals("YEAR"))
            {
                if (selectedYear.equals("YEAR"))
                {
                    selectedYear=yearValues.get(0).toString()
                }
                populateDaySpinner(Calendar.MONTH, Integer.valueOf(selectedYear))
            }else
            {
                populateDaySpinner(Calendar.MONTH, Calendar.YEAR - 1)
            }




        }
    }
    private fun populateDaySpinner(month: Int, year: Int)
    {
        val selctedCalender = Calendar.getInstance()
        selctedCalender[Calendar.MONTH] = month
        selctedCalender[Calendar.YEAR] = year
        val noOfDays = selctedCalender.getActualMaximum(Calendar.DAY_OF_MONTH)
        dayValues= ArrayList()
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
        var monthdataAdapter = CustomSpinnerAdapter(
            mContext,
            R.layout.spinner_textview_item,
            monthValues,
            -1
        )
        dayListView.adapter = monthdataAdapter
        if (dayPosition>=0 && !daySpinner.text.toString().equals("DAY"))
        {
         if (dayPosition < noOfDays)
         {
             daySpinner.setText(dayValues.get(dayPosition).toString())
         }
            else{
             daySpinner.setText(dayValues.get(noOfDays - 1).toString())
         }
        }
        else{
            daySpinner.setText("DAY")
        }

        if (!daySpinner.text.toString().equals("DAY") && !monthSpinner.text.toString().equals("MONTH") && !yearSpinner.text.toString().equals("YEAR"))
        {
            Log.e("CAL MON","CON 1")
            tempArrayList= ArrayList()
            if (calendarDetailArrayListUse.size>0)
            {
                for (i in 0.. calendarDetailArrayListUse.size-1)
                {
                    if (calendarDetailArrayListUse.get(i).dayDate.equals(daySpinner.text.toString()) || calendarDetailArrayListUse.get(
                            i
                        ).dayDate.equals("0" + daySpinner.text.toString()) && calendarDetailArrayListUse.get(
                            i
                        ).yearDate.equals(yearSpinner.text.toString()) && calendarDetailArrayListUse.get(
                            i
                        ).monthDate.toLowerCase().contains(
                            monthSpinner.text.toString().toLowerCase()
                        ))
                    {
                        tempArrayList.add(calendarDetailArrayListUse.get(i))
                    }
                }
                if (tempArrayList.size>0)
                {
                    mListView.visibility=View.VISIBLE
                    val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                    calendarAdapter.notifyDataSetChanged()
                    mListView.adapter = calendarAdapter
                }
                else{
                    mListView.visibility=View.INVISIBLE
                }


            }

        }
        else if(!monthSpinner.text.toString().equals("MONTH") && !yearSpinner.text.toString().equals("YEAR"))
        {
            Log.e("CAL MON","CON 2")
            tempArrayList= ArrayList()
            if (calendarDetailArrayListUse.size>0)
            {
                for (i in 0.. calendarDetailArrayListUse.size-1)
                {
                    if ( calendarDetailArrayListUse.get(i).yearDate.equals(yearSpinner.text.toString()) && calendarDetailArrayListUse.get(
                            i
                        ).monthDate.toLowerCase().contains(
                            monthSpinner.text.toString().toLowerCase()
                        ))
                    {
                        tempArrayList.add(calendarDetailArrayListUse.get(i))
                    }
                }
                if (tempArrayList.size>0)
                {
                    mListView.visibility=View.VISIBLE
                    val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                    calendarAdapter.notifyDataSetChanged()
                    mListView.adapter = calendarAdapter
                }
                else{
                    mListView.visibility=View.INVISIBLE
                }


            }
        }
        else  if (!daySpinner.text.toString().equals("DAY") && !monthSpinner.text.toString().equals("MONTH"))
        {
            Log.e("CAL MON","CON 3")
            tempArrayList= ArrayList()
            if (calendarDetailArrayListUse.size>0)
            {
                for (i in 0.. calendarDetailArrayListUse.size-1)
                {
                    if (calendarDetailArrayListUse.get(i).dayDate.equals(daySpinner.text.toString()) || calendarDetailArrayListUse.get(
                            i
                        ).dayDate.equals("0" + daySpinner.text.toString()) && calendarDetailArrayListUse.get(
                            i
                        ).monthDate.toLowerCase().contains(
                            monthSpinner.text.toString().toLowerCase()
                        ))
                    {
                        tempArrayList.add(calendarDetailArrayListUse.get(i))
                    }
                }
                if (tempArrayList.size>0)
                {
                    mListView.visibility=View.VISIBLE
                    val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                    calendarAdapter.notifyDataSetChanged()
                    mListView.adapter = calendarAdapter
                }
                else{
                    mListView.visibility=View.INVISIBLE
                }


            }

        }
        else  if ( !monthSpinner.text.toString().equals("MONTH"))
        {
            Log.e("CAL MON","CON 4")
            tempArrayList= ArrayList()
            if (calendarDetailArrayListUse.size>0)
            {
                Log.e("CAL MON","CON 41")
                for (i in 0.. calendarDetailArrayListUse.size-1)
                {
                    Log.e("CAL MON","CON 4"+calendarDetailArrayListUse.get(i).monthDate.toLowerCase()+"   "+daySpinner.text.toString().toLowerCase())
                    if (calendarDetailArrayListUse.get(i).monthDate.toLowerCase().contains(
                            monthSpinner.text.toString().toLowerCase()
                        ))
                    {
                        tempArrayList.add(calendarDetailArrayListUse.get(i))
                    }
                }
                if (tempArrayList.size>0)
                {
                    mListView.visibility=View.VISIBLE
                    val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                    calendarAdapter.notifyDataSetChanged()
                    mListView.adapter = calendarAdapter
                }
                else
                {
                    mListView.visibility=View.INVISIBLE
                }

            }

        }
        else  if (!daySpinner.text.toString().equals("DAY"))
        {
            Log.e("CAL MON","CON 5")
            tempArrayList= ArrayList()
            if (calendarDetailArrayListUse.size>0)
            {
                for (i in 0.. calendarDetailArrayListUse.size-1)
                {
                    if (calendarDetailArrayListUse.get(i).dayDate.equals(daySpinner.text.toString()) || calendarDetailArrayListUse.get(
                            i
                        ).dayDate.equals("0" + daySpinner.text.toString()))
                    {
                        tempArrayList.add(calendarDetailArrayListUse.get(i))
                    }
                }
                if (tempArrayList.size>0)
                {
                    mListView.visibility=View.VISIBLE
                    val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                    calendarAdapter.notifyDataSetChanged()
                    mListView.adapter = calendarAdapter
                }
                else{
                    mListView.visibility=View.INVISIBLE
                }


            }

        }
        else  if ( !yearSpinner.text.toString().equals("YEAR"))
        {
            Log.e("CAL MON","CON 6")
            tempArrayList= ArrayList()
            if (calendarDetailArrayListUse.size>0)
            {
                for (i in 0.. calendarDetailArrayListUse.size-1)
                {
                    if (calendarDetailArrayListUse.get(i).yearDate.equals(yearSpinner.text.toString()))
                    {
                        tempArrayList.add(calendarDetailArrayListUse.get(i))
                    }
                }
                if (tempArrayList.size>0)
                {
                    mListView.visibility=View.VISIBLE
                    val calendarAdapter = CalendarListAdapter(mContext, tempArrayList)
                    calendarAdapter.notifyDataSetChanged()
                    mListView.adapter = calendarAdapter
                }
                else
                {
                    mListView.visibility=View.INVISIBLE
                }

            }

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

       var monthdataAdapter = CustomSpinnerAdapter(mContext,R.layout.spinner_textview_item, monthValues, -1)
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
        var yearDataAdapter = CustomSpinnerAdapter(mContext, R.layout.spinner_textview_item, yearValues, -1)
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
        var dayAdapter = CustomSpinnerAdapter(mContext, R.layout.spinner_textview_item, dayValues, -1)
        dayListView.adapter = dayAdapter

    }
    private fun callCalendarDetail() {
        calendarDetailArrayList=ArrayList()
        calendarDetailArrayListUse=ArrayList()
        progressDialog.visibility=View.VISIBLE
        val call: Call<CalendarResponseModel> = ApiClient.getClient.calendar("Bearer " + PreferenceManager.getUserCode(mContext))
        call.enqueue(object : Callback<CalendarResponseModel> {
            override fun onFailure(call: Call<CalendarResponseModel>, t: Throwable) {
                progressDialog.visibility=View.GONE
            }

            override fun onResponse(
                call: Call<CalendarResponseModel>,
                response: Response<CalendarResponseModel>
            ) {
                progressDialog.visibility=View.GONE
                if (response.body()!!.status == 100) {

                    calendarDetailArrayList.addAll(response.body()!!.data.calendarArray)
                    if (calendarDetailArrayList.size > 0) {
                        for (i in 0..calendarDetailArrayList.size - 1) {
                            var model = CalendarArrayModelUSe()

                            model.date = calendarDetailArrayList.get(i).date
                            val str = calendarDetailArrayList.get(i).date
                            val splitStr =
                                str.trim { it <= ' ' }.split("\\s+".toRegex()).toTypedArray()
                            Log.e("SPLITDATA", splitStr[0])
                            model.dayStringDate = splitStr[0]
                            model.dayDate = splitStr[1]
                            model.monthDate = splitStr[2]
                            model.yearDate = splitStr[3]
                            eventModel = ArrayList()
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
                        isClicked=false
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