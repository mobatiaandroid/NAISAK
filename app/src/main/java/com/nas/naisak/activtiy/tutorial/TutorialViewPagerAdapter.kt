package com.nas.naisak.activtiy.tutorial

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.nas.naisak.R
import java.util.ArrayList

class TutorialViewPagerAdapter : PagerAdapter {
    var context: Context
    var imagesarray = ArrayList<Int>()
    lateinit var inflater: LayoutInflater


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View

    }

    override fun getCount(): Int {
        return imagesarray.size
    }


    constructor(context: Context, imagesarray: ArrayList<Int>) {
        this.context = context
        this.imagesarray = imagesarray
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var pageview: View?
        inflater = LayoutInflater.from(context)
        pageview = inflater.inflate(R.layout.swipe_homefragment, null)
        val imageView = pageview?.findViewById<View>(R.id.img) as ImageView
        //Log.e("adapterlength", imagesarray.size.toString())
        if (imagesarray[position] != -1) {
            Glide.with(context).load(imagesarray[position]).centerCrop().into(imageView)

        } else {

        }
        (container as ViewPager).addView(pageview, 0)
        return pageview
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
