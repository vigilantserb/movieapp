package com.stameni.com.movieapp.ui.movies.singleMovie

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.movieActors.MovieActorsResponse
import com.stameni.com.movieapp.models.movieClips.ClipListResponse
import com.stameni.com.movieapp.models.movieImages.Backdrop
import com.stameni.com.movieapp.models.movieReviews.MovieReviewResponse
import com.stameni.com.movieapp.models.singleMovie.SingleMovieResponse
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorListAdapter
import com.stameni.com.movieapp.ui.movies.singleMovie.clips.ClipListAdapter
import com.stameni.com.movieapp.ui.movies.singleMovie.reviews.MovieReviewsAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SingleMovieViewModel : BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val movieImages: MutableLiveData<ArrayList<Backdrop>> = MutableLiveData()

    fun loadImages(id: Int){
        subscription = movieApi.getMovieImages(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it -> it.backdrops }
            .subscribe(
                { result ->
                    onRetrieveImagesSuccess(result)
                },
                { error -> onRetrieveImagesError(error) }
            )
    }

    private fun onRetrieveImagesError(error: Throwable) {
        errorMessage.value = R.string.movie_images_error
    }

    private fun onRetrieveImagesSuccess(result: ArrayList<Backdrop>) {
        movieImages.value = result
    }
}