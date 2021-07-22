package com.nas.naisak.fragment.settings.model

import com.google.gson.annotations.SerializedName

class ChangePasswordApiModel(
    @SerializedName("new_password") val new_password: String,
    @SerializedName("confirm_password") val confirm_password: String,
    @SerializedName("old_password") val old_password: String
)