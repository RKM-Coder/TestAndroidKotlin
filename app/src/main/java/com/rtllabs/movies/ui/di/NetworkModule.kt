package com.rtllabs.movies.ui.di

import com.goalsr.testandroidapp.BuildConfig
import com.rtllabs.movies.ui.data.network.Api
import com.rtllabs.movies.ui.data.network.MovieAppService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single { headerIntercepter() }
    single { okhttpClient(get()) }
    single { retrofit(get()) }
    single { apiService(get()) }
    single { createMoviesApiService(get()) }
}

fun createMoviesApiService(
        api:Api
):MovieAppService= MovieAppService(api)

fun apiService(
        retrofit: Retrofit
):Api=retrofit.create(Api::class.java)

fun retrofit(
        okHttpClient: OkHttpClient
):Retrofit=Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun okhttpClient(
        headerIntercepter:Interceptor
):OkHttpClient =OkHttpClient.Builder().addInterceptor(headerIntercepter)
        .build()

fun headerIntercepter():Interceptor= Interceptor {
    chain ->
    val request =chain.request()
    val  newUrl =request.url().newBuilder()
            .addQueryParameter("api_key",BuildConfig.TMDB_API_KEY).build()
    val  newrequest=request.newBuilder()
            .url(newUrl)
            .build()
    chain.proceed(newrequest)
}