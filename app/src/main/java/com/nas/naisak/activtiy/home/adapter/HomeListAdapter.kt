package com.nas.naisak.activtiy.home.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.TypedArray
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.nas.naisak.R

class HomeListAdapter(
    private val context: Activity,
    private val title: Array<String>,
    private val imgid: TypedArray
) : ArrayAdapter<String>(context, R.layout.custom_homelist, title) {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ViewHolder", "ResourceAsColor")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_homelist, null, true)
        val titleText = rowView.findViewById(R.id.listTxtView) as TextView
        val imageView = rowView.findViewById(R.id.listImg) as ImageView
        titleText.text = title[position]
        imageView.setImageResource(imgid.getResourceId(position, 0))
        return rowView
    }
}

