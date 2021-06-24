package com.rtllabs.movies.ui.display

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.rtllabs.movies.ui.data.model.Movie
import com.rtllabs.movies.ui.data.repository.Repository
import com.rtllabs.movies.ui.data.repository.paged.MoviePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MoviesDisplayViewModel(private val repository: Repository) : ViewModel(){
    
    val movies :Flow<PagingData<MovieModel>> = getMoviesListStream().map {
        pagingData->pagingData.map { MovieModel.MovieItem(it) } }
            .map {
                it.insertSeparators<MovieModel.MovieItem,MovieModel> { before, after ->

                    if (after == null){
                        return@insertSeparators MovieModel.SeparatorItem("End of list")
                }
                    if (before == null){
                        return@insertSeparators null
                    }
                    if (before.roundedVoteCount > after.roundedVoteCount) {
                        if (after.roundedVoteCount >= 1) {
                            MovieModel.SeparatorItem("Less than ${before.roundedVoteCount}0000 vote count")
                        } else {
                            MovieModel.SeparatorItem("Less than 10000 vote count")
                        }
                    } else {
                        // no separator
                        null
                    }

            } }

    private fun getMoviesListStream(): Flow<PagingData<Movie>> {
        return Pager(PagingConfig(20)){
            MoviePagingSource(repository)
        }.flow
    }




}
sealed class MovieModel{
    data class MovieItem(val movie: Movie):MovieModel()
    data class SeparatorItem(val description:String):MovieModel()
}
private val MovieModel.MovieItem.roundedVoteCount:Int
    get() = this.movie.vote_count/1000

