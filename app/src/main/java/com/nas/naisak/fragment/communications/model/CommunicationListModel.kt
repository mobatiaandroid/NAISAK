package com.nas.naisak.fragment.communications.model

import com.google.gson.annotations.SerializedName

class CommunicationListModel(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("url") val url: String = ""
)