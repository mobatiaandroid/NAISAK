package com.nas.naisak.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class Contact  (
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String
)
