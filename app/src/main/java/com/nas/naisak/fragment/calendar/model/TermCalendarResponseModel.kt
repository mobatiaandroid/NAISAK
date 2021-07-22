package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class TermCalendarResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: TermCalendarDataModel

)