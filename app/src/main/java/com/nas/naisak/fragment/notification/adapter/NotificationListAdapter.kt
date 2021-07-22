package com.nas.naisak.fragment.notification.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.fragment.notification.model.NotificationListResponse

internal class NotificationListAdapter (private var notificationList: List<NotificationListResponse>) :
    RecyclerView.Adapter<NotificationListAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView = view.findViewById(R.id.title)
        var status: TextView = view.findViewById(R.id.status)
        var Img: ImageView = view.findViewById(R.id.Img)
        var statusLayout: RelativeLayout = view.findViewById(R.id.statusLayout)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_notification, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = notificationList[position]
        holder.title.text = list.title
        if (list.alertType.equals("Video"))
        {
            holder.Img.setImageResource(R.drawable.alerticon_video)
        }
        else if (list.alertType.equals("Text"))
        {
            holder.Img.setImageResource(R.drawable.alerticon_text)
        }
        else if (list.alertType.equals("Image"))
        {
            holder.Img.setImageResource(R.drawable.alerticon_image)
        }
        else if (list.alertType.equals("Voice"))
        {
            holder.Img.setImageResource(R.drawable.alerticon_audio)
        }
        if(list.read_unread_status==0)
        {
            holder.statusLayout.visibility= View.VISIBLE
            holder.status.text="new"
            holder.status.setBackgroundResource(R.drawable.rectangle_red)
        }
        else if (list.read_unread_status==1)
        {
            holder.statusLayout.visibility= View.GONE
        }
        else if(list.read_unread_status==2)
        {
            holder.statusLayout.visibility= View.VISIBLE
            holder.status.text="updated"
            holder.status.setBackgroundResource(R.drawable.rectangle_blue_update)
        }


    }
    override fun getItemCount(): Int {
        return notificationList.size
    }
}