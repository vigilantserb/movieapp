package com.stameni.com.movieapp.ui.movies.singleMovie.clips

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.LinearLayout
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.ui.movies.singleMovie.MOVIE_ID
import kotlinx.android.synthetic.main.fragment_clips.*

class ClipsFragment : Fragment() {

    lateinit var viewModel: ClipsFragmentViewModel
    private var movieId: Int = 0
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = activity!!.intent!!.extras!!.getInt(MOVIE_ID, 0)
        viewModel = ViewModelProviders.of(this).get(ClipsFragmentViewModel::class.java)

        setHasOptionsMenu(true)

        initClipRecyclerView()
        initErrorHandling()
        initProgressBar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.loadClips(movieId)
        return inflater.inflate(R.layout.fragment_clips, container, false)
    }


    private fun initClipRecyclerView() {
        viewModel.loadingVisibility.observe(this, Observer {
            clip_list_rv.layoutManager = LinearLayoutManager(view!!.context, LinearLayout.VERTICAL, false)
            clip_list_rv.adapter = viewModel.movieReviewsAdapter
        })
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(mRootView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry) {
            viewModel.loadClips(movieId)
        }
        errorSnackbar?.show()
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
}