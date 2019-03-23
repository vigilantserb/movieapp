package com.stameni.com.movieapp.ui.movies.singleMovie.summary

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.ui.movies.singleMovie.MOVIE_ID
import kotlinx.android.synthetic.main.activity_single_movie.*
import kotlinx.android.synthetic.main.fragment_summary.*

class SummaryFragment : Fragment() {

    private lateinit var viewModel: SummaryFragmentViewModel
    private var errorSnackbar: Snackbar? = null
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        movieId = activity!!.intent!!.extras!!.getInt(MOVIE_ID, 0)
        viewModel = ViewModelProviders.of(this).get(SummaryFragmentViewModel::class.java)

        initProgressBar()
        initErrorHandling()
        initMovieData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.loadMovie(movieId)
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }


    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(mRootView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry) {
            viewModel.loadMovie(movieId)
        }
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun initErrorHandling() {
        viewModel.errorMessage.observe(this, Observer {
            if (it != null)
                showError(it)
            else
                hideError()
        })
    }

    private fun initProgressBar() {
        viewModel.loadingVisibility.observe(this, Observer {
            if (it == View.GONE) {
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun initMovieData(){
        viewModel.singleMovie.observe(this, Observer { movie ->
            if (movie != null) {
                var genre = ""
                movie.genres.forEach {
                    genre += it.name + " | "
                }
                title_tv.text = movie.originalTitle
                movie_summary.text = movie.overview
                rating_tv.text = movie.voteAverage.toString()
                release_date_tv.text = movie.releaseDate
                genres.text = " | $genre"
                val url = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}"
                val backgroundUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"

                Picasso.get()
                    .load(backgroundUrl)
                    .fit()
                    .centerInside()
                    .into(backgroundImage)
            }
        })
    }
}