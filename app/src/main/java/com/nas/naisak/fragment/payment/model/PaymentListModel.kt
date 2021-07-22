package com.nas.naisak.fragment.payment.model

import com.google.gson.annotations.SerializedName

class PaymentListModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String
)