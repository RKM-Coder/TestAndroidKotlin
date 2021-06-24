package com.rtllabs.movies.ui.di

import com.rtllabs.movies.ui.data.network.MovieAppService
import com.rtllabs.movies.ui.data.repository.Repository
import org.koin.dsl.module

val repositorymodel= module {
    single { createRepository(get()) }
}
fun createRepository(
        movieAppService: MovieAppService
):Repository= Repository(movieAppService)