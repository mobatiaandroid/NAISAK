package com.nas.naisak.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.nas.naisak.R;


import java.util.ArrayList;


public class CustomPagerAdapter extends PagerAdapter {
    Context context;
    ArrayList<String> bannerimageslist;


    public CustomPagerAdapter(Context context, ArrayList<String> bannerimageslist) {
        this.context = context;
        this.bannerimageslist = bannerimageslist;
    }

    @Override
    public int getCount() {
        return bannerimageslist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View pageview;
        LayoutInflater mInflaters = LayoutInflater.from(context);
        pageview = mInflaters.inflate(R.layout.custombanner_layout, null);
        ImageView imageView = pageview.findViewById(R.id.adImg);

        if (!bannerimageslist.get(position).equals("")) {
            Glide.with(context)
                    .load(bannerimageslist.get(position))
                    .fitCenter()
                    .into(imageView);

        }else {
            Glide.with(context)
                    .load(R.drawable.default_banner)
                    .fitCenter()
                    .into(imageView);
        }
        container.addView(pageview, 0);

        return pageview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
