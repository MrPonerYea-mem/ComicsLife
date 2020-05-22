package com.MrPonerYea.comicslife.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemMarket {
    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("login")
    @Expose
    var login: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("success")
    @Expose
    var success: String? = null
}