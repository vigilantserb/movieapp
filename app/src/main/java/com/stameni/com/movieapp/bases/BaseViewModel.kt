package com.stameni.com.movieapp.bases

import android.arch.lifecycle.ViewModel
import com.stameni.com.movieapp.di.component.DaggerViewModelInjector
import com.stameni.com.movieapp.di.component.ViewModelInjector
import com.stameni.com.movieapp.di.module.NetworkModule
import com.stameni.com.movieapp.ui.movies.MovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorActivityViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MovieViewModel -> injector.inject(this)
            is SingleMovieViewModel -> injector.inject(this)
            is ActorActivityViewModel -> injector.inject(this)
        }
    }
}