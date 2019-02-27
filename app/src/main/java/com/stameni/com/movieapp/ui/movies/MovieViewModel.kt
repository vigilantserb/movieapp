package com.stameni.com.movieapp.ui.movies

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.api.TMDBApi
import com.stameni.com.movieapp.bases.BaseViewModel
import com.stameni.com.movieapp.models.movieList.ServerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieViewModel : BaseViewModel() {

    @Inject
    lateinit var movieApi: TMDBApi

    private lateinit var subscription: Disposable

    val movieListAdapter: MovieListAdapter = MovieListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    val errorClickListener = View.OnClickListener {
        loadTopRatedMovies()
    }

    init {
        loadTopRatedMovies()
    }

    fun loadPopularMovies(page: Int = 1) {
        subscription = movieApi.getPopularMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMovieListStart() }
            .doOnTerminate { onRetrieveMovieListFinish() }
            .doOnError { onRetrievePostMovieError() }
            .subscribe { result ->
                if (result.page == 1) onRetrievePostMovieSuccess(result)
                else {
                    movieListAdapter.updateMovieList(result)
                }
            }
    }

    fun loadTopRatedMovies(page: Int = 1) {
        subscription = movieApi.getTopRatedMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMovieListStart() }
            .doOnTerminate { onRetrieveMovieListFinish() }
            .subscribe(
                { result ->
                    if (result.page == 1) onRetrievePostMovieSuccess(result)
                    else {
                        movieListAdapter.updateMovieList(result)
                    }
                },
                { onRetrievePostMovieError() }
            )
    }

    fun loadUpcomingMovies(page: Int = 1) {
        subscription = movieApi.getUpcomingMovies(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveMovieListStart() }
            .doOnTerminate { onRetrieveMovieListFinish() }
            .subscribe(
                { result ->
                    if (result.page == 1) onRetrievePostMovieSuccess(result)
                    else {
                        movieListAdapter.updateMovieList(result)
                    }
                },
                { onRetrievePostMovieError() }
            )
    }

    fun loadSearchedMovies(name: String, page: Int = 1) {
        if (!name.isEmpty())
            subscription = movieApi.getMovieSearch(name, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinct()
                .doOnSubscribe { onRetrieveMovieListStart() }
                .doOnTerminate { onRetrieveMovieListFinish() }
                .subscribe(
                    { result ->
                        if (result.page == 1) onRetrieveMovieSearchSuccessful(result)
                        else {
                            movieListAdapter.updateMovieList(result)
                        }
                    },
                    { error -> onRetrieveMovieSearchError(error) }
                )
    }

    private fun onRetrieveMovieSearchError(error: Throwable) {
        errorMessage.value = R.string.post_error
    }

    private fun onRetrieveMovieSearchSuccessful(result: ServerResponse) {
        movieListAdapter.setPostList(result)
    }

    fun onNextPage(x: Int = 1, type: Int) {
        if (type == POPULAR)
            loadPopularMovies(x)
        if (type == UPCOMING)
            loadUpcomingMovies(x)
        if (type == TOP_RATED)
            loadTopRatedMovies(x)
        if (type == SEARCH)
            loadSearchedMovies(SEARCH_TERM, x)
    }

    private fun onRetrievePostMovieError() {
        errorMessage.value = R.string.post_error
    }

    private fun onRetrievePostMovieSuccess(result: ServerResponse) {
        movieListAdapter.setPostList(result)
    }

    private fun onRetrieveMovieListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveMovieListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}