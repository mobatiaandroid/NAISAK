package com.nas.naisak.fragment.parentsessentials.Model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.notification.model.NotificationDataResponse

class ParentsEssentialResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: ParentsEssentialDataModel
)