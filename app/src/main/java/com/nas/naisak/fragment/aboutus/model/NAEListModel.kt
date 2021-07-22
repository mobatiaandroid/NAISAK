package com.nas.naisak.fragment.aboutus.model

import com.google.gson.annotations.SerializedName

class NAEListModel (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String = ""
)