package com.nas.naisak.fragment.communications.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialBannerModel
import com.nas.naisak.fragment.parentsessentials.Model.ParentsEssentialListModel

class CommunicationDataModel (
    @SerializedName("banner_image") val banner_image: String,
    @SerializedName("description") val description: String,
    @SerializedName("contact_email") val contact_email: String,
    @SerializedName("lists") val parents_essentials: List<CommunicationListModel>

)