package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarArrayModelUSe {
    @SerializedName("date") var date: String=""
    @SerializedName("dayStringDate") var dayStringDate: String=""
    @SerializedName("dayDate") var dayDate: String=""
    @SerializedName("monthDate") var monthDate: String=""
    @SerializedName("yearDate") var yearDate: String=""
    @SerializedName("details") var details: ArrayList<CalendarDetailsModelUse> = ArrayList()
}