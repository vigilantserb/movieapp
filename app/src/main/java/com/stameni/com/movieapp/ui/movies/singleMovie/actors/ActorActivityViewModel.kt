package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.actorDetails.SingleActorResponse
import com.stameni.com.movieapp.models.actorMovies.ActorMoviesResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActorActivityViewModel : BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val singleActor: MutableLiveData<SingleActorResponse> = MutableLiveData()
    val actorMovies: MutableLiveData<ActorMoviesResponse> = MutableLiveData()

    val actorMovieListAdapter = ActorMoviesAdapter()

    fun loadActor(id: Int) {
        subscription = movieApi.getSingleActor(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveActorStart() }
            .doOnTerminate { onRetrieveActorFinish() }
            .subscribe(
                { result ->
                    onRetrieveActorSuccess(result)
                },
                { onRetrieveActorError() }
            )
    }

    fun loadActorMovies(id: Int) {
        subscription = movieApi.getActorMovies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    onRetrieveActorMoviesSuccess(result)
                },
                { error -> onRetrieveActorMoviesError(error) }
            )
    }

    private fun onRetrieveActorMoviesError(error: Throwable) {
        errorMessage.value = R.string.actors_error
    }

    private fun onRetrieveActorMoviesSuccess(result: ActorMoviesResponse) {
        errorMessage.value = null
        actorMovies.value = result
        actorMovieListAdapter.setActorMoviesList(result)
    }

    private fun onRetrieveActorError() {
        errorMessage.value = R.string.actors_error
    }

    private fun onRetrieveActorSuccess(result: SingleActorResponse) {
        singleActor.value = result
    }

    private fun onRetrieveActorFinish() {
        loadingVisibility.value = View.GONE
        errorMessage.value = null
    }

    private fun onRetrieveActorStart() {
        loadingVisibility.value = View.VISIBLE
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}