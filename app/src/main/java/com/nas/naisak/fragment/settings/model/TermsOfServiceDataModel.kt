package com.nas.naisak.fragment.settings.model

import com.google.gson.annotations.SerializedName

class TermsOfServiceDataModel  (
    @SerializedName("lists") val lists: List<TermsOfServiceListModel>
)