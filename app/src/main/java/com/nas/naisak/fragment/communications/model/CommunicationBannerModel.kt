package com.nas.naisak.fragment.communications.model

import com.google.gson.annotations.SerializedName

class CommunicationBannerModel (

    @SerializedName("banner_image") val banner_image: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("contact_email") val contact_email: String = ""
)