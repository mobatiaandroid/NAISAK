package com.nas.naisak.customviews.edittext

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import com.nas.naisak.R

class CustomFontEditTextSemiBoldBlack : androidx.appcompat.widget.AppCompatEditText{
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        // ...
        val type= Typeface.createFromAsset(context.assets,"fonts/SourceSansPro-Semibold.otf")
        this.setTypeface(type)
        this.setTextColor(resources.getColor(R.color.black))
    }

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0) {
        val type =
            Typeface.createFromAsset(context.assets, "fonts/SourceSansPro-Semibold.otf")
        this.setTypeface(type)
        this.setTextColor(context.resources.getColor(R.color.black))
    }
}