package com.nas.naisak.fragment.communications.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialDataModel

class CommunicationResponseModel  (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: CommunicationDataModel
)