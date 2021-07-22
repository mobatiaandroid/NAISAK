package com.nas.naisak.commonadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nas.naisak.R
import com.nas.naisak.commonmodels.StudentDataListResponse

internal class StudentListAdapter  (private var context: Context, private var studentList: List<StudentDataListResponse>) :
    RecyclerView.Adapter<StudentListAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var listTxtTitle: TextView = view.findViewById(R.id.listTxtTitle)
        var listTxtClass: TextView = view.findViewById(R.id.listTxtClass)
        var imagicon: ImageView = view.findViewById(R.id.imagicon)
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_student_list, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = studentList[position]
        holder.listTxtTitle.text = movie.studentName
        holder.listTxtClass.text = movie.section
        if(!movie.photo.equals(""))
        {
            Glide.with(context) //1
                .load(movie.photo)
                .placeholder(R.drawable.boy)
                .error(R.drawable.boy)
                .skipMemoryCache(true) //2
                .diskCacheStrategy(DiskCacheStrategy.NONE) //3
                .transform(CircleCrop()) //4
                .into(holder.imagicon)
        }
        else{
            holder.imagicon.setImageResource(R.drawable.boy)
        }
    }
    override fun getItemCount(): Int {
        return studentList.size
    }
}