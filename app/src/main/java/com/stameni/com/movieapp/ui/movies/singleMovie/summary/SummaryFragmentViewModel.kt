package com.stameni.com.movieapp.ui.movies.singleMovie.summary

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.singleMovie.SingleMovieResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SummaryFragmentViewModel: BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val singleMovie: MutableLiveData<SingleMovieResponse> = MutableLiveData()

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