package com.nas.naisak.fragment.notification.model

import com.google.gson.annotations.SerializedName

class NotificationResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: NotificationDataResponse
)