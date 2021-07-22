package com.nas.naisak.activtiy.communication.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.activtiy.communication.model.SocialMediaListModel

class SocialMediaAdapter (private var primarydetailslist: List<SocialMediaListModel>, private var mContext: Context) :
    RecyclerView.Adapter<SocialMediaAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mTitleTxt: TextView = view.findViewById(R.id.listTxtTitle)
        var imagicon: ImageView = view.findViewById(R.id.imagicon)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_social_media, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = primarydetailslist[position]

        val sdk = Build.VERSION.SDK_INT
        if (list.title.contains("Facebook"))
        {
            holder.imagicon.setImageResource(R.drawable.facebookiconmedia)
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.imagicon.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.roundfb)
                )

            } else {
                holder.imagicon.background = mContext.resources.getDrawable(R.drawable.roundfb)
            }
            holder.mTitleTxt.text = list.title.replace("Facebook:"," ")
        }
        else  if (list.title.contains("Twitter"))
        {

            holder.imagicon.setImageResource(R.drawable.twittericon)
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.imagicon.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.roundtw)
                )

            } else {
                holder.imagicon.background = mContext.resources.getDrawable(R.drawable.roundtw)
            }
            holder.mTitleTxt.text = list.title.replace("Twitter:"," ")
        }
        else  if (list.title.contains("Instagram"))
        {
            holder.imagicon.setImageResource(R.drawable.instagramicon)
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                holder.imagicon.setBackgroundDrawable(
                    mContext.resources.getDrawable(R.drawable.roundins)
                )

            } else {
                holder.imagicon.background = mContext.resources.getDrawable(R.drawable.roundins)
            }
            holder.mTitleTxt.text = list.title.replace("Instagram:"," ")
        }

    }
    override fun getItemCount(): Int {
        return primarydetailslist.size
    }
}