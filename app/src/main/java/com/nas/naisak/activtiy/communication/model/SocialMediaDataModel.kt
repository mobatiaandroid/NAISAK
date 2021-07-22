package com.nas.naisak.activtiy.communication.model

import com.google.gson.annotations.SerializedName

class SocialMediaDataModel(
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("lists") val detaillists: ArrayList<SocialMediaListModel>

)