package com.beniezsche.junoassignment.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class APOD {

    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("explanation")
    @Expose
    var explanation: String? = null
    @SerializedName("hdurl")
    @Expose
    var hdurl: String? = null
    @SerializedName("media_type")
    @Expose
    var media_type: String? = null
}