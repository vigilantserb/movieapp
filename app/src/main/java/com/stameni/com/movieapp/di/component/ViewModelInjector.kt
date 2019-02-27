package com.stameni.com.movieapp.di.component

import com.stameni.com.movieapp.di.module.NetworkModule
import com.stameni.com.movieapp.ui.movies.MovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovieViewModel
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorActivityViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjector{

    fun inject(moviesViewModel: MovieViewModel)
    fun inject(singleMovieViewModel: SingleMovieViewModel)
    fun inject(actorActivityViewModel: ActorActivityViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}