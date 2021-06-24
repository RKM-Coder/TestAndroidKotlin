package com.rtllabs.movies.ui.data.network

import com.rtllabs.movies.ui.data.model.Result
import com.rtllabs.movies.ui.data.network.response.MoviesListResponse

class MovieAppService(private val api: Api) :BaseService() {

    suspend fun fetchPopularMovies(page:Int):Result<MoviesListResponse>{
        return createCall { api.getLatesMovies(page) }
    }
}