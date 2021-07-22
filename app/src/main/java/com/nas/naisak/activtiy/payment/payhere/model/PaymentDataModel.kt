package com.nas.naisak.activity.payment.payhere.model

import com.google.gson.annotations.SerializedName

class PaymentDataModel (

    @SerializedName("lists") val lists: List<PaymentListModel>

        )