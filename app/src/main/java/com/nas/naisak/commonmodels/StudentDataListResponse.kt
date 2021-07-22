package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName

class StudentDataListResponse (
    @SerializedName("id") val studentId: Int,
    @SerializedName("name") val studentName: String,
    @SerializedName("wallet") val wallet: Int,
    @SerializedName("student_class") val studentClass: String,
    @SerializedName("section") val section: String,
    @SerializedName("house") val house: String,
    @SerializedName("photo") val photo: String
)