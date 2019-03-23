package com.stameni.com.movieapp.ui.movies.singleMovie.clips

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.movieClips.ClipListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ClipsFragmentViewModel: BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val movieReviewsAdapter: ClipListAdapter = ClipListAdapter()

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

    private fun onRetrieveClipError() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = R.string.review_error
    }

    private fun onRetrieveClipSuccess(result: ClipListResponse) {
        errorMessage.value = null
        loadingVisibility.value = View.GONE
        movieReviewsAdapter.setClipList(result)
    }
}
