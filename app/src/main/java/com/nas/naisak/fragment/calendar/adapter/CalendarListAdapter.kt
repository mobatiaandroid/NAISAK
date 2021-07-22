package com.nas.naisak.fragment.calendar.adapter

import android.app.Dialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.provider.CalendarContract
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
import com.nas.naisak.constants.recyclermanager.OnItemClickListener
import com.nas.naisak.constants.recyclermanager.addOnItemClickListener
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe
import com.nas.naisak.fragment.calendar.model.CalendarDetailsModelUse


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


}