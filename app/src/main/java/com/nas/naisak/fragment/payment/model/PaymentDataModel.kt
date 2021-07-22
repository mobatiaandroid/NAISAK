package com.nas.naisak.fragment.payment.model

import com.google.gson.annotations.SerializedName

class PaymentDataModel (

    @SerializedName("lists") val lists: List<PaymentListModel>

)