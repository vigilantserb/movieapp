package com.stameni.com.movieapp.ui.movies.singleMovie

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import kotlinx.android.synthetic.main.activity_single_movie.*

const val MOVIE_ID = "movie_id"
const val MAX_IMAGE_COUNT = 15

class SingleMovie : AppCompatActivity() {

    private lateinit var viewModel: SingleMovieViewModel
    private var errorSnackbar: Snackbar? = null
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_movie)

        viewpager_main.adapter = SingleMovieViewPagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)
        movieId = intent!!.extras!!.getInt(MOVIE_ID, 0)
        viewModel = ViewModelProviders.of(this).get(SingleMovieViewModel::class.java)
        viewModel.loadImages(movieId)

        initErrorHandling()
        initCarouselView()
    }

    private fun initCarouselView() {
        viewModel.movieImages.observe(this, Observer {
            if (it != null) {
                myImageView.setImageListener { position, imageView ->
                    Picasso.get()
                        .load("https://image.tmdb.org/t/p/w500/${it[position].filePath}")
                        .resize(0, it[position].height)
                        .into(imageView)
                }
                if(it.size < MAX_IMAGE_COUNT) {
                    myImageView.pageCount = it.size
                }else{
                    myImageView.pageCount = MAX_IMAGE_COUNT
                }
            }
        })
    }

    private fun initErrorHandling() {
        viewModel.errorMessage.observe(this, Observer {
            if (it != null)
                showError(it)
            else
                hideError()
        })
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(mRootView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry) {
            viewModel.loadImages(movieId)
        }
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}