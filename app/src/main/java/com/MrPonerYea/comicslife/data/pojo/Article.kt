package com.MrPonerYea.comicslife.data.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Article {
    @SerializedName("author")
    @Expose
    var author: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("messgae")
    @Expose
    var messgae: String? = null
    @SerializedName("date")
    @Expose
    var date: String? = null

}