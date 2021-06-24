package com.rtllabs.restApisample.network

import com.google.gson.GsonBuilder
import com.rtllabs.movies.ui.di.okhttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        private var retrofit:Retrofit?=null
        fun getApiClient():Retrofit{
            val gson =GsonBuilder()
                    .setLenient().create()
            val okHttpClient= OkHttpClient.Builder()
                    .readTimeout(100, TimeUnit.SECONDS )
                    .connectTimeout(100,TimeUnit.MINUTES)
                    .build()
            if (retrofit == null){
                retrofit =Retrofit.Builder()
                        .baseUrl(BASEURL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

            }
            return retrofit!!
        }
    }
}

const val BASEURL = "https://jsonplaceholder.typicode.com/"