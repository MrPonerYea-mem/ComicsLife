package com.MrPonerYea.comicslife.data.network

import com.MrPonerYea.comicslife.data.pojo.AllArticles
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.data.pojo.ItemMarket
import com.MrPonerYea.comicslife.data.pojo.User
import retrofit2.Call
import retrofit2.http.*


interface JSONPlaceHolderApi {
    @GET("login")
    fun getLoginWithEmailandPassword(@QueryMap options: Map<String, String>): Call<GetLogin?>?

    @Headers("Content-Type: application/json")
    @POST("registration")
    fun registration1(@Body user: User): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("registration")
    fun registration(@Field("email") email: String, @Field("login") login: String, @Field("password") pssword: String): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("setArticle")
    fun addArticle(@Field("author") author: String, @Field("title") title: String, @Field("text") text: String): Call<GetLogin?>?

    @GET("getArticles")
    fun getArticle(): Call<AllArticles?>?

    @GET("getComics")
    fun getComics(): Call<ItemMarket?>?

    @FormUrlEncoded
    @POST("setComics")
    fun setComics(
        @Field("title") title: String, @Field("description") description: String, @Field("phone") phone: String,
        @Field("login") login: String, @Field("address") address: String, @Field("price") price: String
    ): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("getUserArticle")
    fun getUserArticle(@Field("login") login: String): Call<AllArticles?>?

    @FormUrlEncoded
    @POST("getUserComics")
    fun getUserComics(@Field("login") login: String): Call<ItemMarket?>?

    @FormUrlEncoded
    @POST("updateProfile")
    fun updateProfile(@Field("login") login: String, @Field("email") email: String, @Field("password") password: String): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("updateArticle")
    fun updateArticle(@Field("id") id: Int, @Field("title") title: String, @Field("text") text: String): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("updateComics")
    fun updateComics(
        @Field("title") title: String, @Field("description") description: String, @Field("phone") phone: String,
        @Field("address") address: String, @Field("price") price: String, @Field("idComics") idComics: Int
    ): Call<GetLogin?>?
}