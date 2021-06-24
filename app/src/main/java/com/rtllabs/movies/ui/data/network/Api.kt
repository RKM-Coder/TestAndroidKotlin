package com.rtllabs.movies.ui.data.network

import com.rtllabs.movies.ui.data.network.response.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("3/discover/movie?sort_by=vote_count.desc")
    suspend fun getLatesMovies(
            @Query("page") page:Int
    ): Response<MoviesListResponse>
}