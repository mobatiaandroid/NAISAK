package com.nas.naisak.fragment.calendar.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.payment.model.PaymentDataModel

class CalendarResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: CalendarDataModel

)