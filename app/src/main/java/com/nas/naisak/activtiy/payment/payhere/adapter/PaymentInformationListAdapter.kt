package com.nas.naisak.activtiy.payment.payhere.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activity.common_model.DetailListitems

internal class PaymentInformationListAdapter (private var primarydetailslist: List<DetailListitems>) :
    RecyclerView.Adapter<PaymentInformationListAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTitleTxt: TextView = view.findViewById(R.id.listTxtTitle)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_information_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = primarydetailslist[position]
        holder.mTitleTxt.text = list.title

    }
    override fun getItemCount(): Int {
        return primarydetailslist.size
    }
}