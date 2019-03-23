package com.stameni.com.movieapp.ui.movies.singleMovie.reviews

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.movieReviews.MovieReviewResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ReviewsFragmentViewModel: BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()
    val movieReviewsAdapter: MovieReviewsAdapter = MovieReviewsAdapter()

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

    private fun onRetrieveReviewsError() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = R.string.review_error
    }

    private fun onRetrieveReviewsSuccess(result: MovieReviewResponse) {
        errorMessage.value = null
        loadingVisibility.value = View.GONE
        movieReviewsAdapter.setReviewList(result)
    }
}
