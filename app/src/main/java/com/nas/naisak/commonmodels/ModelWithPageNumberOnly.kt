package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName

class ModelWithPageNumberOnly (
    @SerializedName("page_number") val page_number: String
)