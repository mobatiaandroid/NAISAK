package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName

class ModelStatusMsgData (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ModelWithEmailDescBanner
)