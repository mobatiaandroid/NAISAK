package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName

class ModelWithEmailDescBanner (
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("description") val description: String,
    @SerializedName("contact_email") val contact_email: String
)