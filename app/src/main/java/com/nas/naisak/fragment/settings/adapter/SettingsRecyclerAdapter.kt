package com.nas.naisak.fragment.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R

internal class SettingsRecyclerAdapter  (private var notificationList: List<String>) :
    RecyclerView.Adapter<SettingsRecyclerAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_settings, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = notificationList[position]
        holder.listTxtTitle.text = list.toString()

    }
    override fun getItemCount(): Int {
        return notificationList.size
    }
}