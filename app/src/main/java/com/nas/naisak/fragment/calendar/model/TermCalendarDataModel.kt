package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class TermCalendarDataModel (
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("lists") val lists: List<TermCalendarListModel>
)