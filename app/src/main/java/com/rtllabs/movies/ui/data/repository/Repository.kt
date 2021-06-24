package com.rtllabs.movies.ui.data.repository

import com.rtllabs.movies.ui.data.model.Result
import com.rtllabs.movies.ui.data.network.MovieAppService
import com.rtllabs.movies.ui.data.network.response.MoviesListResponse


class Repository(private val service:MovieAppService) {
    suspend fun getPopularMovies(page:Int):MoviesListResponse{
        return when(val result =service.fetchPopularMovies(page)){
            is Result.Success ->result.data
            is Result.Error ->throw result.error

        }
    }
}