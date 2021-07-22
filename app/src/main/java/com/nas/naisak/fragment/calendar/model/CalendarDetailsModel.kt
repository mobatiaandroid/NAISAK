package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarDetailsModel (
    @SerializedName("id") val id: Int,
    @SerializedName("starttime") val starttime: String,
    @SerializedName("endtime") val endtime: String,
    @SerializedName("title") val title: String,
    @SerializedName("isAllday") val isAllday: String,
    @SerializedName("vpml") val vpml: String,
    @SerializedName("status") val status: String
)