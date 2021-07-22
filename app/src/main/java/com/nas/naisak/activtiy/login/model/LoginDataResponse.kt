package com.nas.naisak.activtiy.login.model

import com.google.gson.annotations.SerializedName

class LoginDataResponse (
    @SerializedName("user_details") val user_details: UserDetailModel,
    @SerializedName("token") val token: String

)