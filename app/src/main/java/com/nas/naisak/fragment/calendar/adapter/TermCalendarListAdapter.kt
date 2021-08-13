package com.nas.naisak.fragment.calendar.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activtiy.payment.payhere.adapter.PaymentListAdapter
import com.nas.naisak.fragment.calendar.model.TermCalendarListModel
import com.nas.naisak.fragment.payment.model.PaymentListModel
import java.util.ArrayList

class TermCalendarListAdapter (private var mContext: Context, private var repoetDetailArray: ArrayList<TermCalendarListModel>) :
    RecyclerView.Adapter<TermCalendarListAdapter.MyViewHolder>() {
    lateinit var clickedurl:String

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var termname: TextView = view.findViewById(R.id.listTxtTitle)
//        var clickLinear: LinearLayout = view.findViewById(R.id.clickLinear)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_parents_essential, parent, false)
        return MyViewHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val summary = repoetDetailArray[position]
        holder.termname.text = repoetDetailArray[position].title

    }
    override fun getItemCount(): Int {

        return repoetDetailArray.size

    }
}