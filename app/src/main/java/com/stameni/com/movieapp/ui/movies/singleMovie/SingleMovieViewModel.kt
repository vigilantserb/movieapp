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
    val reviewsVisibility: MutableLiveData<Int> = MutableLiveData()
    val singleMovie: MutableLiveData<SingleMovieResponse> = MutableLiveData()
    val movieImages: MutableLiveData<ArrayList<Backdrop>> = MutableLiveData()

    val clipListAdapter: ClipListAdapter = ClipListAdapter()
    val actorListAdapter: ActorListAdapter = ActorListAdapter()
    val movieReviewsAdapter: MovieReviewsAdapter = MovieReviewsAdapter()

    fun loadMovie(id: Int) {
        subscription = movieApi.getSingleMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMovieStart() }
            .doOnTerminate { onRetrieveMovieFinish() }
            .subscribe(
                { result ->
                    onRetrievePostMovieSuccess(result)
                },
                { onRetrievePostMovieError() }
            )
    }

    fun loadClips(id: Int) {
        subscription = movieApi.getMovieClips(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    onRetrieveClipSuccess(result)
                },
                { onRetrieveClipError() }
            )
    }

    fun loadActors(id: Int){
        subscription = movieApi.getMovieActors(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    onRetrieveActorsSuccess(result)
                },
                { onRetrieveActorsError() }
            )
    }

    fun loadReviews(id: Int, page: Int = 1){
        subscription = movieApi.getMovieReviews(id, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    onRetrieveReviewsSuccess(result)
                },
                { onRetrieveReviewsError() }
            )
    }

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
        println(error)
    }

    private fun onRetrieveImagesSuccess(result: ArrayList<Backdrop>) {
        movieImages.value = result
    }

    private fun onRetrieveReviewsError() {
        errorMessage.value = R.string.review_error
    }

    private fun onRetrieveReviewsSuccess(result: MovieReviewResponse) {
        if(result.results.isEmpty())
            reviewsVisibility.value = View.GONE
        movieReviewsAdapter.setReviewList(result)
    }

    private fun onRetrieveActorsError() {
        errorMessage.value = R.string.actors_error
    }

    private fun onRetrieveActorsSuccess(result: MovieActorsResponse) {
        errorMessage.value = null
        actorListAdapter.setActorList(result)
    }

    private fun onRetrieveClipError() {
        errorMessage.value = R.string.clip_error
    }

    private fun onRetrieveClipSuccess(result: ClipListResponse) {
        errorMessage.value = null
        clipListAdapter.setClipList(result)
    }

    private fun onRetrievePostMovieError() {
        errorMessage.value = R.string.post_error
    }

    private fun onRetrievePostMovieSuccess(result: SingleMovieResponse?) {
        singleMovie.value = result
    }

    private fun onRetrieveMovieFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

}