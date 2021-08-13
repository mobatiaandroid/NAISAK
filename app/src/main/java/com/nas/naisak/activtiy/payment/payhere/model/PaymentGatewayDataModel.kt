package com.nas.naisak.activtiy.payment.payhere.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.activtiy.communication.model.SocialMediaListModel

class PaymentGatewayDataModel (
    @SerializedName("lists") val lists: PaymentGatewayListModel
)