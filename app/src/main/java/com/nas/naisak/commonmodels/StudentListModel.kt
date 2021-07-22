package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName

class StudentListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("message") val message: String,
    @SerializedName("validation_errors") val validationErrorArray: List<String>,
    @SerializedName("data") val dataArray:StudentDataModel
)