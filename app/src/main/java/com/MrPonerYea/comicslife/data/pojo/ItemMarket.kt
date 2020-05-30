package com.MrPonerYea.comicslife.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemMarket {
    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("comics")
    @Expose
    val comics: List<Comics>? = null
}