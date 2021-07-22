package com.nas.naisak.activity.common_model

import com.google.gson.annotations.SerializedName

data class DataResponse (
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("lists") val lists: List<Listitems>
)
