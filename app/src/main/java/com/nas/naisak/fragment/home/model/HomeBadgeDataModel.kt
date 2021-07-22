package com.nas.naisak.fragment.home.model

import com.google.gson.annotations.SerializedName

class HomeBadgeDataModel (
    @SerializedName("calendar_badge") val calendar_badge: Int,
    @SerializedName("calendar_edited_badge") val calendar_edited_badge: Int,
    @SerializedName("notification_badge") val notification_badge: Int,
    @SerializedName("notification_edited_badge") val notification_edited_badge: Int,
    @SerializedName("paymentitem_badge") val paymentitem_badge: Int,
    @SerializedName("paymentitem_edit_badge") val paymentitem_edit_badge: Int

)