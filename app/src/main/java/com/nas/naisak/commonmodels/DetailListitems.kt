package com.nas.naisak.activity.common_model

import com.google.gson.annotations.SerializedName

data class DetailListitems (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("url") val url: String = ""
    )
