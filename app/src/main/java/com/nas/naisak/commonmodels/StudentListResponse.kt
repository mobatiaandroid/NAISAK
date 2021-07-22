package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.payment.model.PaymentDataModel

class StudentListResponse (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: PaymentDataModel

)