package com.nas.naisak.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class Contactusresponse (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val contactusdata: Contactdata
)
