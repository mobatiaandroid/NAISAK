package com.nas.naisak.fragment.contactus.model

import com.google.gson.annotations.SerializedName

class Contactdata(
    @SerializedName("contact_description") val description: String,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("contacts") val contacts: List<Contact>
)
