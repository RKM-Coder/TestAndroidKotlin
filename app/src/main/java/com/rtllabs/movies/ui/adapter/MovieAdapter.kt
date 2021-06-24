package com.rtllabs.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goalsr.testandroidapp.R
import com.goalsr.testandroidapp.databinding.MovieItemBinding
import com.goalsr.testandroidapp.databinding.MovieItemSeperatorBinding
import com.rtllabs.movies.ui.display.MovieModel
import java.lang.UnsupportedOperationException


class MovieAdapter: PagingDataAdapter<MovieModel,RecyclerView.ViewHolder>(MovieModelComparator) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieModel: MovieModel = getItem(position)!!

        movieModel.let {
            when (movieModel) {
                is MovieModel.MovieItem -> {
                    val viewHolder = holder as MovieViewHolder
                    viewHolder.movieItemBinding.movieTitle.text = movieModel.movie.original_title
                    viewHolder.movieItemBinding.movieVoteCount.text =
                            "Vote count ${movieModel.movie.vote_count}"
                }
                is MovieModel.SeparatorItem -> {
                    val viewHolder = holder as MovieSeperator
                    viewHolder.movieItemSeperatorBinding
                            .separatorDescription.text = movieModel.description
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.movie_item->MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> {
                MovieSeperator(MovieItemSeperatorBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is MovieModel.MovieItem->R.layout.movie_item
            is MovieModel.SeparatorItem->R.layout.movie_item_seperator
            null->throw UnsupportedOperationException("Unknownerror")

        }
    }

    class MovieViewHolder(val movieItemBinding: MovieItemBinding):RecyclerView.ViewHolder(movieItemBinding.root)
    class MovieSeperator(val movieItemSeperatorBinding: MovieItemSeperatorBinding):RecyclerView.ViewHolder(movieItemSeperatorBinding.root)
companion object{
    val MovieModelComparator = object : DiffUtil.ItemCallback<MovieModel>() {
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return (oldItem is MovieModel.MovieItem && newItem is MovieModel.MovieItem &&
                    oldItem.movie.id == newItem.movie.id) ||
                    (oldItem is MovieModel.SeparatorItem && newItem is MovieModel.SeparatorItem &&
                            oldItem.description == newItem.description)
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
                oldItem == newItem
    }
}


}
