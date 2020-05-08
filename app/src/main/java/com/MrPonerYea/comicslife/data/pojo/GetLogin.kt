package com.MrPonerYea.comicslife.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GetLogin {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null

    @SerializedName("message")
    @Expose
    var message: String? = null

    @SerializedName("user")
    @Expose
    var user: User? = null
}