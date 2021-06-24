package com.rtllabs.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.goalsr.testandroidapp.databinding.LoadStateViewBinding

class MovieLoadStateAdpter(private val retry: ()->Unit): LoadStateAdapter<MovieLoadStateAdpter.LoadStateViewHolder>() {
    class LoadStateViewHolder(val loadStateViewBinding: LoadStateViewBinding) :RecyclerView.ViewHolder(loadStateViewBinding.root)

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val progress=holder.loadStateViewBinding.loadStateProgress
        var btnretry=holder.loadStateViewBinding.loadStateRetry
        var  txtErrorMessage=holder.loadStateViewBinding.loadStateErrorMessage

        progress.isVisible=loadState is LoadState.Loading
        btnretry.isVisible=loadState !is LoadState.Loading
        txtErrorMessage.isVisible =loadState !is LoadState.Loading

        if (loadState is LoadState.Error){
            txtErrorMessage.text=loadState.error.localizedMessage
        }
        btnretry.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
                LoadStateViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }


}