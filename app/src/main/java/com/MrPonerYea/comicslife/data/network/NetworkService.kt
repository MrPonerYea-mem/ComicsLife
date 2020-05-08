package com.MrPonerYea.comicslife.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory




class NetworkService private constructor() {
    companion object {
        private var mInstance: NetworkService? = null
        private val BASE_URL: String = "http://192.168.0.13/ComicsLife.ru/"
        //"http://192.168.0.13/kotlinchat.com/rest_api/"

        fun getInstance(): NetworkService? {
            if (mInstance == null) {
                mInstance = NetworkService()
            }
            return mInstance
        }
    }

    private var mRetrofit: Retrofit? = null

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
          //  .client(dd)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getJSONApi(): JSONPlaceHolderApi? {
        return mRetrofit!!.create(JSONPlaceHolderApi::class.java)
    }
}