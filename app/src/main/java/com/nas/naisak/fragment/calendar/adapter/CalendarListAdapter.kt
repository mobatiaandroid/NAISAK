package com.nas.naisak.fragment.calendar.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe



class CalendarListAdapter(private var mContext: Context, private var parentsEssentialArrayList: List<CalendarArrayModelUSe>) : RecyclerView.Adapter<CalendarListAdapter.MyViewHolder>() {
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
        var colours: IntArray = mContext.getResources().getIntArray(R.array.calendar_row_colors)
        colorValue = colours[position % colours.size]
        val movie = parentsEssentialArrayList[position]
        linearLayoutManager = LinearLayoutManager(mContext)
        holder.eventsListView.layoutManager = linearLayoutManager
        holder.title.text = movie.date
        holder.header.setBackgroundColor(colorValue)
        val calendarAdapter = CalendarDetailListAdapter(mContext,parentsEssentialArrayList.get(position).details, colorValue,position, isRead)
        holder.eventsListView.adapter = calendarAdapter


    }
    override fun getItemCount(): Int {

        return parentsEssentialArrayList.size

    }


}