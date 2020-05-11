package com.MrPonerYea.comicslife.data.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class AllArticles {
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("articles")
    @Expose
    var articles: List<Article?>? = null
}