package com.nas.naisak.activtiy.communication.model

import com.google.gson.annotations.SerializedName

class SocialMediaResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val data: SocialMediaDataModel
)