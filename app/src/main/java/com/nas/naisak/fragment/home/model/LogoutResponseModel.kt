package com.nas.naisak.fragment.home.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.communications.model.CommunicationDataModel

class LogoutResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: ArrayList<String>
)