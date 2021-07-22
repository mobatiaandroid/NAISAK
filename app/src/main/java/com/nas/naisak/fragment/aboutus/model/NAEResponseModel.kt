package com.nas.naisak.fragment.aboutus.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.activity.common_model.DataDetailResponse

class NAEResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: NAEDataModel
)