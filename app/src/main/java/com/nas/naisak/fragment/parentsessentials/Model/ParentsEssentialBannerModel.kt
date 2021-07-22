package com.nas.naisak.fragment.parentsessentials.Model

import com.google.gson.annotations.SerializedName

class ParentsEssentialBannerModel(

    @SerializedName("banner_image") val banner_image: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("contact_email") val contact_email: String = ""
)