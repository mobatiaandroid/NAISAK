package com.nas.naisak.fragment.calendar.adapter

import android.app.Dialog
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.constants.AppController
import com.nas.naisak.constants.CommonMethods
import com.nas.naisak.constants.PreferenceManager
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe
import com.nas.naisak.fragment.calendar.model.CalendarDetailsModelUse
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

var mnthId:Int=-1
class CalendarListAdapter(
    private var mContext: Context,
    private var parentsEssentialArrayList: List<CalendarArrayModelUSe>
) : RecyclerView.Adapter<CalendarListAdapter.MyViewHolder>() {
    lateinit var linearLayoutManager: LinearLayoutManager
    var colorValue:Int=0
    var isRead:Boolean=false
     inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.dateNTime)
        var header: LinearLayout = view.findViewById(R.id.header)
        var eventsListView: RecyclerView = view.findViewById(R.id.eventsListView)


    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_calendar_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var mPosition:Int=position
        var colours: IntArray = mContext.getResources().getIntArray(R.array.calendar_row_colors)
        colorValue = colours[position % colours.size]
        val movie = parentsEssentialArrayList[position]
        linearLayoutManager = LinearLayoutManager(mContext)
        holder.eventsListView.layoutManager = linearLayoutManager
        holder.title.text = movie.date
        holder.header.setBackgroundColor(colorValue)
        Log.e("ADAPTER SIZE", parentsEssentialArrayList.get(position).details.size.toString())
        val calendarAdapter = CalendarDetailListAdapter(
            mContext, parentsEssentialArrayList.get(
                position
            ).details, colorValue, position, isRead
        )
        holder.eventsListView.adapter = calendarAdapter
        holder.eventsListView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                var dateString: String = ""
                if (parentsEssentialArrayList.get(mPosition).details.get(position).isAllday.equals("1")) {
                    dateString = "All Day Event"
                } else {
                    if (!parentsEssentialArrayList.get(mPosition).details.get(position).starttime.equals(
                            ""
                        ) && !parentsEssentialArrayList.get(mPosition).details.get(position).endtime.equals(
                            ""
                        )
                    ) {
                        dateString =
                            parentsEssentialArrayList.get(mPosition).details.get(position).starttime + " - " + parentsEssentialArrayList.get(
                                mPosition
                            ).details.get(position).endtime
                    } else if (!parentsEssentialArrayList.get(mPosition).details.get(position).starttime.equals(
                            ""
                        ) && parentsEssentialArrayList.get(mPosition).details.get(position).endtime.equals(
                            ""
                        )
                    ) {
                        dateString =
                            parentsEssentialArrayList.get(mPosition).details.get(position).starttime
                    } else if (parentsEssentialArrayList.get(mPosition).details.get(position).starttime.equals(
                            ""
                        ) && !parentsEssentialArrayList.get(mPosition).details.get(position).endtime.equals(
                            ""
                        )
                    ) {
                        dateString =
                            parentsEssentialArrayList.get(mPosition).details.get(position).endtime
                    }
                }
                showCalendarDetail(
                    parentsEssentialArrayList.get(mPosition).details.get(position).title,
                    parentsEssentialArrayList.get(
                        mPosition
                    ).date,
                    dateString,
                    parentsEssentialArrayList.get(mPosition).details,
                    position,
                    mContext
                )

            }

        })

    }

    fun showCalendarDetail(
        eventNameStr: String,
        eventDateStr: String,
        eventTypeStr: String,
        mCalendarEventModels: ArrayList<CalendarDetailsModelUse>,
        eventPosition: Int,
        mContext: Context
    )
    {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialogue_calendar_detail)
        var iconImageView = dialog.findViewById(R.id.iconImageView) as? ImageView
        var eventType = dialog.findViewById(R.id.eventType) as? TextView
        var eventDate = dialog.findViewById(R.id.eventDate) as? TextView
        var dismiss = dialog.findViewById(R.id.dismiss) as Button
        var linkBtn = dialog.findViewById(R.id.linkBtn) as Button
        var deleteCalendar = dialog.findViewById(R.id.deleteCalendar) as Button
        var addToCalendar = dialog.findViewById(R.id.addToCalendar) as Button
        eventDate?.text =eventDateStr
        eventType?.text = "( "+eventTypeStr+" )"
        dismiss.setOnClickListener()
        {
            dialog.dismiss()
        }
        if(mCalendarEventModels.get(eventPosition).vpml.equals(""))
        {
            linkBtn.visibility=View.GONE
        }
        else{
            linkBtn.visibility=View.VISIBLE
        }
        linkBtn.setOnClickListener(View.OnClickListener {

            val uri = Uri.parse(mCalendarEventModels[eventPosition].vpml)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            mContext.startActivity(intent)
            dialog.dismiss()

        })

        addToCalendar.setOnClickListener(View.OnClickListener {

            val reqSentence: String = CommonMethods.htmlparsing(
                Html.fromHtml(mCalendarEventModels[eventPosition].title).toString().replace(
                    "\\s+".toRegex(),
                    " "
                )
            ).toString()
            val splited = reqSentence.split("\\s+".toRegex()).toTypedArray()
            var dateString: Array<String?>
            var year = -1
            var month = -1
            var day = -1
            val timeString: Array<String>
            var hour = -1
            var min = -1
            val timeString1: Array<String>
            var hour1 = -1
            var min1 = -1
            var allDay: String? = "0"
            year = mCalendarEventModels[eventPosition].yearDate.toInt()
            Log.e("MONTH" ,mCalendarEventModels[eventPosition].monthDate)
            month = getMonthDetails(mContext, mCalendarEventModels[eventPosition].monthDate)
            day = mCalendarEventModels[eventPosition].dayDate.toInt()
            if (mCalendarEventModels[eventPosition].starttime.equals("")) {
                hour = -1
                min = -1
            } else {
                val format1: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val format2 = SimpleDateFormat("hh:mm", Locale.ENGLISH)
                val dateStart = format1.parse(mCalendarEventModels[eventPosition].starttime)
                val startTime = format2.format(dateStart)
                timeString = startTime.split(":").toTypedArray()
                Log.e("From Time",startTime)
                hour = timeString[0].toInt()
                min = timeString[1].toInt()
            }
            allDay = mCalendarEventModels[eventPosition].isAllday

            if (mCalendarEventModels[eventPosition].endtime.equals("")) {
                hour1 = -1
                min1 = -1
            } else {
                val format1: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val format2 = SimpleDateFormat("hh:mm", Locale.ENGLISH)
                val dateStart = format1.parse(mCalendarEventModels[eventPosition].endtime)
                val startTime = format2.format(dateStart)
                timeString1 = startTime.split(":").toTypedArray()
                Log.e("From Time",startTime)
                hour = timeString1[0].toInt()
                min = timeString1[1].toInt()
//                timeString1 = mCalendarEventModels[eventPosition].endtime.split(":").toTypedArray()
//                hour = timeString1[0].toInt()
//                min = timeString1[1].toInt()
            }

            var addToCalendar = true
            val prefData: List<String> = PreferenceManager
                .getCalendarEventNames(mContext)!!.split(",")
            for (i in prefData.indices) {
                if (prefData[i].equals(
                        mCalendarEventModels[eventPosition].title + mCalendarEventModels[eventPosition].title,
                        ignoreCase = true
                    )
                ) {
                    addToCalendar = false
                    break
                }
            }
            if (addToCalendar) {
                Log.e("Values", year.toString()+" M "+month.toString()+" D "+day.toString()+ " H "+hour.toString()+" MIN "+min.toString())
                if (year != -1 && month != -1 && day != -1 && hour != -1 && min != -1) {
                    Log.e("It Enters","First Condition")
                    if (hour1 == -1 && min1 == -1) {
                        Log.e("It Enters","Second Condition")
                        addReminder(year, month, day, hour, min, year, month, day,hour, min,
                            mCalendarEventModels[eventPosition].title, mCalendarEventModels[eventPosition].title, 0,
                            eventPosition, allDay, mCalendarEventModels)
                    }
                    else {
                        Log.e("It Enters","Third Condition")
                        addReminder(year, month, day, hour, min, year, month, day, hour1, min1,
                            mCalendarEventModels[eventPosition].title, mCalendarEventModels[eventPosition].title, 0,
                            eventPosition, allDay, mCalendarEventModels)

                    }
                    Log.e("It Enters","Fourth Condition")
                }
                else {
                    Log.e("It Enters","Fifth Condition")
                    Toast.makeText(
                        mContext,
                        "Not enough details to add to calendar",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(mContext, "Event added to device calendar", Toast.LENGTH_SHORT)
                    .show()
            }
            notifyDataSetChanged()

        })

        deleteCalendar.setOnClickListener(View.OnClickListener {

            if (mCalendarEventModels.get(eventPosition).id != 0) {
                val deleteUri = ContentUris.withAppendedId(
                    CalendarContract.Events.CONTENT_URI, mCalendarEventModels[eventPosition]
                        .id.toLong()
                )
                mContext.contentResolver.delete(
                    deleteUri, null,
                    null
                )
                mCalendarEventModels[eventPosition].id = 0
                Toast.makeText(
                    mContext,
                    "Event removed from device calendar", Toast.LENGTH_SHORT
                ).show()
            }
            dialog.dismiss()
        })

        dialog.show()
    }
    override fun getItemCount(): Int {

        return parentsEssentialArrayList.size

    }

    fun getMonthDetails(mContext: Context, descStringTime: String):Int
    {
        if (descStringTime.equals("January"))
        {
            mnthId=0
        }
        else if (descStringTime.equals("February"))
        {
            mnthId=1
        }
        else if (descStringTime.equals("March"))
        {
            mnthId=2
        }
        else if (descStringTime.equals("April"))
        {
            mnthId=3
        }
        else if (descStringTime.equals("May"))
        {
            mnthId=4
        }
        else if (descStringTime.equals("June"))
        {
            mnthId=5
        }
        else if (descStringTime.equals("July"))
        {
            mnthId=6
        }
        else if (descStringTime.equals("August"))
        {
            mnthId=7
        }
        else if (descStringTime.equals("September"))
        {
            mnthId=8
        }
        else if (descStringTime.equals("October"))
        {
            mnthId=9
        }
        else if (descStringTime.equals("November"))
        {
            mnthId=10
        }
        else if (descStringTime.equals("December"))
        {
            mnthId=11
        }

        return mnthId
    }

    fun addReminder(startYear: Int, startMonth: Int, startDay: Int, startHour: Int, startMinute: Int, endYear: Int, endMonth: Int,
        endDay: Int, endHour: Int, endMinutes: Int, name: String, description: String, count: Int, position: Int, allDay: String,
        mCalendarEventModelAdd:ArrayList<CalendarDetailsModelUse>) {
        val beginTime = Calendar.getInstance()
        beginTime[startYear, startMonth, startDay, startHour] = startMinute
        val startMillis = beginTime.timeInMillis
        val endTime = Calendar.getInstance()
        endTime[endYear, endMonth, endDay, endHour] = endMinutes
        val endMillis = endTime.timeInMillis
        val eventUriString = "content://com.android.calendar/events"
        val eventValues = ContentValues()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            // Marshmallow+
            eventValues.put(CalendarContract.Events.CALENDAR_ID, 3) //1
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP || Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1) {
            // lollipop
            eventValues.put(CalendarContract.Events.CALENDAR_ID, 3) //1
        } else {
            //below Marshmallow
            eventValues.put(CalendarContract.Events.CALENDAR_ID, 1) //1
        }

//        eventValues.put(CalendarContract.Events.CALENDAR_ID, 1);//1
        eventValues.put(CalendarContract.Events.TITLE, name)
        eventValues.put(CalendarContract.Events.DESCRIPTION, description)
        eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.SHORT)
        eventValues.put(CalendarContract.Events.DTSTART, startMillis)
        eventValues.put(CalendarContract.Events.DTEND, endMillis)
        if (allDay == "1") {
            eventValues.put(CalendarContract.Events.ALL_DAY, true)
        } else {
            eventValues.put(CalendarContract.Events.ALL_DAY, false)
        }
        eventValues.put("eventStatus", 1)
        eventValues.put(CalendarContract.Events.HAS_ALARM, 1)
        val eventUri = mContext.contentResolver.insert(
            Uri.parse(eventUriString), eventValues
        )
        val eventID = eventUri!!.lastPathSegment!!.toLong()
        Log.d("TAG", "1----$eventID")
        mCalendarEventModelAdd[position].id.toString()
        Log.d("TAG", "2----")
        PreferenceManager.setCalendarEventNames(
            mContext,
            PreferenceManager.getCalendarEventNames(mContext).toString() + name
                    + description + ","
        )
        if (count == 0) {
            Toast.makeText(
                mContext,"Event added to device calendar", Toast.LENGTH_SHORT
            ).show()
        }
        /***************** Event: Reminder(with alert) Adding reminder to event  */
        val reminderUriString = "content://com.android.calendar/reminders"
        val reminderValues = ContentValues()
        reminderValues.put("event_id", eventID)
        reminderValues.put("minutes", 1440)
        reminderValues.put("method", 1)
        val reminderUri = mContext.contentResolver.insert(
            Uri.parse(reminderUriString), reminderValues
        )
        val eventIDlong = reminderUri!!.lastPathSegment!!.toLong()
        AppController.eventIdList.add(eventID.toString())
    }
}