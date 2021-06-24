package com.rtllabs.movies.ui.di

import com.rtllabs.movies.ui.display.MoviesDisplayViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel { MoviesDisplayViewModel(get()) }
}