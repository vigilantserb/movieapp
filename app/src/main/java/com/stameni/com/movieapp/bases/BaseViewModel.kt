package com.stameni.com.movieapp.bases

import android.arch.lifecycle.ViewModel
import com.stameni.com.movieapp.di.component.DaggerViewModelInjector
import com.stameni.com.movieapp.di.component.ViewModelInjector
import com.stameni.com.movieapp.di.module.NetworkModule
import com.stameni.com.movieapp.ui.movies.MovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorActivityViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.clips.ClipsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.reviews.ReviewsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.summary.SummaryFragmentViewModel

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
            is SummaryFragmentViewModel -> injector.inject(this)
            is ReviewsFragmentViewModel -> injector.inject(this)
            is ClipsFragmentViewModel -> injector.inject(this)
            is ActorsFragmentViewModel -> injector.inject(this)
        }
    }
}