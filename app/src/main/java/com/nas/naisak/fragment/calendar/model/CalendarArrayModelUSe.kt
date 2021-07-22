package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarArrayModelUSe {
    @SerializedName("date") var date: String=""
    @SerializedName("details") var details: ArrayList<CalendarDetailsModelUse> = ArrayList()
}