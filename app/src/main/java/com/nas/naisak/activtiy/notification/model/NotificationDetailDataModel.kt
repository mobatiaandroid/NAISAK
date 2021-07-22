package com.nas.naisak.activtiy.notification.model

import com.google.gson.annotations.SerializedName

class NotificationDetailDataModel (
    @SerializedName("message") val message: String,
    @SerializedName("url") val url: String,
    @SerializedName("time_stamp") val time_stamp: String
)
