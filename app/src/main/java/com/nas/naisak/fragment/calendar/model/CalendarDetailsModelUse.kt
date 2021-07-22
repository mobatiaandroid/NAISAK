package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarDetailsModelUse {

    @SerializedName("id") var id: Int=0
    @SerializedName("starttime") var starttime: String=""
    @SerializedName("endtime") var endtime: String=""
    @SerializedName("title") var title: String=""
    @SerializedName("isAllday") var isAllday: String=""
    @SerializedName("vpml") var vpml: String=""
    @SerializedName("status") var status: String=""
    @SerializedName("dayStringDate") var dayStringDate: String=""
    @SerializedName("dayDate") var dayDate: String=""
    @SerializedName("monthDate") var monthDate: String=""
    @SerializedName("yearDate") var yearDate: String=""
    @SerializedName("from_time") var fromTime: String=""
    @SerializedName("to_time") var toTime: String=""
    @SerializedName("eventAddToCalendar") var eventAddToCalendar: Boolean=false
}