package com.nas.naisak.activtiy.payment.payhere.model

import com.google.gson.annotations.SerializedName

class PaymentGatewayApiModel (
    @SerializedName("payment_id") val payment_id: String,
    @SerializedName("amount") val amount: String,
    @SerializedName("invoice_no") val invoice_no: String,
    @SerializedName("email") val email: String,
    @SerializedName("payment_method") val payment_method: String,
    @SerializedName("device_type") val device_type: String,
    @SerializedName("device_name") val device_name: String,
    @SerializedName("app_version") val app_version: String
)