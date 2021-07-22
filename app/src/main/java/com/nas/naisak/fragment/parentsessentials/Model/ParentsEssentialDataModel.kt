package com.nas.naisak.fragment.parentsessentials.Model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.notification.model.NotificationListResponse

class ParentsEssentialDataModel (
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("description") val description: String,
    @SerializedName("contact_email") val contact_email: String,
    @SerializedName("lists") val parents_essentials: List<ParentsEssentialListModel>

    )