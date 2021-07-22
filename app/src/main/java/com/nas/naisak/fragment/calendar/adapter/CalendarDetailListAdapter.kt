package com.nas.naisak.fragment.calendar.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.nas.naisak.R
import com.nas.naisak.fragment.calendar.model.CalendarArrayModelUSe
import com.nas.naisak.fragment.calendar.model.CalendarDetailsModelUse

class CalendarDetailListAdapter (private var mContext: Context, private var parentsEssentialArrayList: List<CalendarDetailsModelUse>,private var colors:Int,private var mPosition:Int,private var isRead:Boolean) : RecyclerView.Adapter<CalendarDetailListAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var eventName: TextView = view.findViewById(R.id.eventName)
        var eventTime: TextView = view.findViewById(R.id.eventTime)
        var addicon: ImageView = view.findViewById(R.id.addicon)
        var removeicon: ImageView = view.findViewById(R.id.removeicon)



    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_calendar_detail, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       Log.e("ADAPTAR","DETAIL")
      holder.eventName.setText(parentsEssentialArrayList.get(position).title)
      holder.eventTime.setText(parentsEssentialArrayList.get(position).starttime)
      holder.eventTime.setTextColor(colors)
      holder.eventName.setTextColor(colors)

      if (colors == mContext.resources.getColor(R.color.cal_row_1))
      {
          holder.addicon.setImageResource(R.drawable.addicon4)
          holder.removeicon.setImageResource(R.drawable.minimize4)
      }
      else  if (colors == mContext.resources.getColor(R.color.cal_row_2))
      {
          holder.addicon.setImageResource(R.drawable.addicon3)
          holder.removeicon.setImageResource(R.drawable.minimize3)
      }
      else  if (colors == mContext.resources.getColor(R.color.cal_row_3))
      {
          holder.addicon.setImageResource(R.drawable.addicon2)
          holder.removeicon.setImageResource(R.drawable.minimize2)
      }
      else  if (colors == mContext.resources.getColor(R.color.cal_row_4))
      {
          holder.addicon.setImageResource(R.drawable.addicon1)
          holder.removeicon.setImageResource(R.drawable.minimize1)
      }

    }
    override fun getItemCount(): Int {

        return parentsEssentialArrayList.size

    }

}