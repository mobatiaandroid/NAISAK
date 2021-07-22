package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName
import com.nas.naisak.activity.common_model.DataDetailResponse

class CommonDetailResponse (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: DataDetailResponse
)