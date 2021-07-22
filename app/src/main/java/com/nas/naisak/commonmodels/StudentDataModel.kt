package com.nas.naisak.commonmodels

import com.google.gson.annotations.SerializedName

class StudentDataModel (
    @SerializedName("lists") val studentListArray: List<StudentDataListResponse>
    //student_list
)