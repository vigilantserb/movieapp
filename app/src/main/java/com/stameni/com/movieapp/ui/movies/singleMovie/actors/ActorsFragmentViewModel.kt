package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.movieActors.MovieActorsResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ActorsFragmentViewModel: BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val actorListAdapter: ActorListAdapter = ActorListAdapter()

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

    private fun onRetrieveActorsError() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = R.string.review_error
    }

    private fun onRetrieveActorsSuccess(result: MovieActorsResponse) {
        errorMessage.value = null
        loadingVisibility.value = View.GONE
        actorListAdapter.setActorList(result)
    }

}
