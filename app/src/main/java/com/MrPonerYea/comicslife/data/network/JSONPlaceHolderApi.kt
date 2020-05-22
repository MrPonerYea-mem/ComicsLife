package com.MrPonerYea.comicslife.data.network

import com.MrPonerYea.comicslife.data.pojo.AllArticles
import com.MrPonerYea.comicslife.data.pojo.GetLogin
import com.MrPonerYea.comicslife.data.pojo.ItemMarket
import com.MrPonerYea.comicslife.data.pojo.User
import retrofit2.Call
import retrofit2.http.*


interface JSONPlaceHolderApi {
    @GET("login")
    fun getLoginWithEmailandPassword(@QueryMap options:Map<String, String>): Call<GetLogin?>?

    @GET("login1.php")
    fun getWithLog(@Query("email")  email:String): Call<GetLogin?>?

    @Headers("Content-Type: application/json")
    @POST("registration")
    fun registration1(@Body user: User): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("registration")
    fun registration(@Field("email") email:String, @Field("login") login:String, @Field("password") pssword:String): Call<GetLogin?>?

    @FormUrlEncoded
    @POST("setArticle")
    fun addArticle(@Field("author") author:String, @Field("title") title:String, @Field("text") text:String): Call<GetLogin?>?

    @GET("getArticles")
    fun getArticle(): Call<AllArticles?>?

    @GET("getComics")
    fun getComics(): Call<ItemMarket?>?
}