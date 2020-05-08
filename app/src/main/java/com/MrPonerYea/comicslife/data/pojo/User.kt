package com.MrPonerYea.comicslife.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User(var login_sp:String?, var email_sp: String?, var password_sp:String?) {
    @SerializedName("login")
    @Expose
    var login: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("password")
    @Expose
    var password: String? = null
}