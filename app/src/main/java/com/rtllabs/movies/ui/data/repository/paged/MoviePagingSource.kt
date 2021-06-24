package com.rtllabs.movies.ui.data.repository.paged

import androidx.paging.PagingSource
import com.rtllabs.movies.ui.data.model.Movie
import com.rtllabs.movies.ui.data.repository.Repository

class MoviePagingSource(private val repository: Repository) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPage = params.key ?: 1
            val moviesListResponse = repository.getPopularMovies(nextPage)
            LoadResult.Page(
                    data = moviesListResponse.results!!,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (nextPage < moviesListResponse.totalPages!!)
                        moviesListResponse.page?.plus(1)
                    else null
            )
        }catch (e:Exception){
            LoadResult.Error(e)
        }
    }
}