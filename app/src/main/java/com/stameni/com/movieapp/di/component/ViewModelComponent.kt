package com.stameni.com.movieapp.di.component

import com.stameni.com.movieapp.di.module.NetworkModule
import com.stameni.com.movieapp.ui.movies.MovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorActivityViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.clips.ClipsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.reviews.ReviewsFragmentViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.summary.SummaryFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelComponent{

    fun inject(moviesViewModel: MovieViewModel)
    fun inject(singleMovieViewModel: SingleMovieViewModel)
    fun inject(actorActivityViewModel: ActorActivityViewModel)
    fun inject(summaryFragmentViewModel: SummaryFragmentViewModel)
    fun inject(reviewsFragmentViewModel: ReviewsFragmentViewModel)
    fun inject(clipsFragmentViewModel: ClipsFragmentViewModel)
    fun inject(activityFragmentViewModel: ActorsFragmentViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelComponent

        fun networkModule(networkModule: NetworkModule): Builder
    }
}