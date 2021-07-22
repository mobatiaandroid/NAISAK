package com.nas.naisak.activity.common_model

import com.google.gson.annotations.SerializedName

data class Listitems (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String = "")
