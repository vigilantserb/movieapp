package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.ui.movies.singleMovie.MOVIE_ID
import kotlinx.android.synthetic.main.fragment_actors.*

class ActorsFragment : Fragment() {

    lateinit var viewModel: ActorsFragmentViewModel
    private var errorSnackbar: Snackbar? = null
    private var movieId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        movieId = activity!!.intent!!.extras!!.getInt(MOVIE_ID, 0)
        viewModel = ViewModelProviders.of(this).get(ActorsFragmentViewModel::class.java)

        initActorsRecyclerView()
        initErrorHandling()
        initProgressBar()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.loadActors(movieId)
        return inflater.inflate(R.layout.fragment_actors, container, false)
    }

    private fun initActorsRecyclerView() {
        viewModel.loadingVisibility.observe(this, Observer {
            actor_list_rv.layoutManager = GridLayoutManager(view!!.context, 3, GridLayoutManager.VERTICAL, false)
            actor_list_rv.adapter = viewModel.actorListAdapter
        })
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(mRootView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry) {
            viewModel.loadActors(movieId)
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