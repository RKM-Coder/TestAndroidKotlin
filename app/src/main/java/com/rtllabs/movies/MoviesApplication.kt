package com.rtllabs.movies

import android.app.Application
import com.rtllabs.movies.ui.di.networkModule
import com.rtllabs.movies.ui.di.repositorymodel
import com.rtllabs.movies.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(listOf(networkModule, repositorymodel, viewModelModule))
        }
    }
}