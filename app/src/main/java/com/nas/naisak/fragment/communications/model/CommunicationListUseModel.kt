package com.nas.naisak.fragment.communications.model

import com.google.gson.annotations.SerializedName

class CommunicationListUseModel {
    @SerializedName("id") var id: Int = 0
    @SerializedName("title") var title: String = ""
    @SerializedName("url") var url: String = ""
}