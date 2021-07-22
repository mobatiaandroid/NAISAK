package com.nas.naisak.fragment.notification.model

import com.google.gson.annotations.SerializedName

class NotificationListResponse (

    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("alert_type") val alertType: String = "",
    @SerializedName("read_unread_status") val read_unread_status: Int = 0
)

