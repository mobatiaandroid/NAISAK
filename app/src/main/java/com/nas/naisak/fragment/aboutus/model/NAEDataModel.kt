package com.nas.naisak.fragment.aboutus.model

import com.google.gson.annotations.SerializedName
import com.nas.naisak.activity.common_model.DataDetailResponse

class NAEDataModel (
    @SerializedName("lists") val list: List<NAEListModel>
        )