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

        viewModel.loadMovie(movieId)
        viewModel.loadClips(movieId)
        viewModel.loadActors(movieId)
        viewModel.loadReviews(movieId)
        viewModel.loadImages(movieId)

        initErrorHandling()
        initCarouselView()
//        initClipRecyclerView()
//        initActorsRecyclerView()
//        initReviewsRecyclerView()
        initFields()
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
                myImageView.pageCount = it.size
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

//    private fun initClipRecyclerView() {
//        clip_list_rv.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
//        clip_list_rv.adapter = viewModel.clipListAdapter
//    }
//
//    private fun initActorsRecyclerView() {
//        actor_list_rv.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
//        actor_list_rv.adapter = viewModel.actorListAdapter
//    }
//
//    private fun initReviewsRecyclerView() {
//        review_list_rv.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//        review_list_rv.adapter = viewModel.actorListAdapter
//    }

    private fun initFields() {
        viewModel.singleMovie.observe(this, Observer { movie ->
            if (movie != null) {
                var genre = ""
                movie.genres.forEach {
                    genre += it.name + " | "
                }
//                title_tv.text = movie.originalTitle
//                movie_summary.text = movie.overview
//                rating_tv.text = movie.voteAverage.toString()
//                release_date_tv.text = movie.releaseDate
//                genres.text = " | $genre"
                val url = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}"
                val backgroundUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"

//                Picasso.get()
//                    .load(backgroundUrl)
//                    .transform(BlurTransformation(this, 10))
//                    .fit()
//                    .centerInside()
//                    .into(backgroundImage)
            }
        })

//        viewModel.reviewsVisibility.observe(this, Observer {
//            if (it != null) {
//                if (it == View.GONE) {
//                    review_list_rv.visibility = View.GONE
//                    review_tv.visibility = View.GONE
//                }
//            }
//        })
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(mRootView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry) {
            viewModel.loadMovie(movieId)
            viewModel.loadClips(movieId)
            viewModel.loadActors(movieId)
            viewModel.loadReviews(movieId)
        }
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }
}