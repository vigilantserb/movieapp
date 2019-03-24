package com.stameni.com.movieapp.bases

import android.arch.lifecycle.ViewModel
import com.stameni.com.movieapp.di.component.DaggerViewModelComponent
import com.stameni.com.movieapp.di.component.ViewModelComponent
import com.stameni.com.movieapp.di.module.NetworkModule
import com.stameni.com.movieapp.ui.movies.MovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorActivityViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.clips.ClipsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.reviews.ReviewsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.summary.SummaryFragmentViewModel

abstract class BaseViewModel: ViewModel(){
    private val component: ViewModelComponent = DaggerViewModelComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MovieViewModel -> component.inject(this)
            is SingleMovieViewModel -> component.inject(this)
            is ActorActivityViewModel -> component.inject(this)
            is SummaryFragmentViewModel -> component.inject(this)
            is ReviewsFragmentViewModel -> component.inject(this)
            is ClipsFragmentViewModel -> component.inject(this)
            is ActorsFragmentViewModel -> component.inject(this)
        }
    }
}