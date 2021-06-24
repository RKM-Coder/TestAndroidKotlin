package com.rtllabs.movies.ui.display

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.goalsr.testandroidapp.databinding.ActivityMoviesDisplayBinding
import com.rtllabs.movies.ui.adapter.MovieAdapter
import com.rtllabs.movies.ui.adapter.MovieLoadStateAdpter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
class MoviesDisplayActivity : AppCompatActivity() {

    private lateinit var displayBinding: ActivityMoviesDisplayBinding
    private val viewModel:MoviesDisplayViewModel by viewModel()
    private lateinit var movieadapter:MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayBinding= ActivityMoviesDisplayBinding.inflate(layoutInflater)
        setContentView(displayBinding.root)
        movieadapter= MovieAdapter()

        displayBinding.movieRecyclerview.apply {
            layoutManager=LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter=movieadapter.withLoadStateFooter(
                    footer = MovieLoadStateAdpter{
                        movieadapter.retry()
                    }
            )
        }

        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                movieadapter.submitData(it)
            }
        }

        displayBinding.btnRetry.setOnClickListener {
            movieadapter.retry()
        }

        movieadapter.addLoadStateListener {
            loadstate->
            if (loadstate.refresh is LoadState.Loading){

                displayBinding.btnRetry.visibility=View.GONE
                displayBinding.progressbar.visibility=View.VISIBLE
            }else{

                displayBinding.progressbar.visibility=View.GONE
                val errorstate=when{
                    loadstate.append is LoadState.Error->loadstate.append as LoadState.Error
                    loadstate.prepend is LoadState.Error ->loadstate.prepend as LoadState.Error
                    loadstate.refresh is LoadState.Error ->{
                        displayBinding.btnRetry.visibility=View.VISIBLE
                        loadstate.refresh as LoadState.Error
                    }
                    else -> null
                }

                errorstate?.let {
                    Toast.makeText(this,it.error.message,Toast.LENGTH_LONG).show()
                }
            }
        }


    }
}