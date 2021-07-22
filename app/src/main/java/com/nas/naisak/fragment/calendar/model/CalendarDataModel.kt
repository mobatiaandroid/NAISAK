package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarDataModel (
    @SerializedName("calendar") val calendarArray: List<CalendarArrayModel>
        )