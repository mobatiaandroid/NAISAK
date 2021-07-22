package com.nas.naisak.fragment.notification.model

import com.google.gson.annotations.SerializedName

class NotificationDataResponse (
    @SerializedName("lists") val lists: List<NotificationListResponse>
)