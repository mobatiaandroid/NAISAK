package com.nas.naisak.activtiy.notification.model

import com.google.gson.annotations.SerializedName

class MessageDetailResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: NotificationDetailDataModel

)