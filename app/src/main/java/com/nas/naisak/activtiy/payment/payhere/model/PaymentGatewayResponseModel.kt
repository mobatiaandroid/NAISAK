package com.nas.naisak.activtiy.payment.payhere.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.activity.payment.payhere.model.PaymentDetailDataModel

class PaymentGatewayResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: PaymentGatewayDataModel
)