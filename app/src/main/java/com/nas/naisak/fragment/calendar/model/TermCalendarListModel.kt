package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class TermCalendarListModel (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)