package com.nas.naisak.activity.payment.payhere.model

import com.google.gson.annotations.SerializedName

class PaymentListModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)