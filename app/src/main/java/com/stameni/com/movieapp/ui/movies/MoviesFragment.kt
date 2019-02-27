package com.stameni.com.movieapp.ui.movies

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.util.OnBottomReachedListener
import kotlinx.android.synthetic.main.fragment_movies.*

const val POPULAR = 0
const val TOP_RATED = 1
const val UPCOMING = 2
const val SEARCH = 3
var SEARCH_TERM = ""

class MoviesFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private var errorSnackbar: Snackbar? = null
    private var CURRENT_TYPE = TOP_RATED
    private var FORMER_CURRENT_TYPE = TOP_RATED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

        initRecyclerView()
        initProgressBar()
        initErrorHandling()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        initSearchView(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (CURRENT_TYPE != SEARCH)
            FORMER_CURRENT_TYPE = CURRENT_TYPE
        when (item.itemId) {
            R.id.action_popular -> {
                viewModel.loadPopularMovies()
                CURRENT_TYPE = POPULAR
                return true
            }
            R.id.action_upcoming -> {
                viewModel.loadUpcomingMovies()
                CURRENT_TYPE = UPCOMING
                return true
            }
            R.id.action_top_rated -> {
                viewModel.loadTopRatedMovies()
                CURRENT_TYPE = TOP_RATED
                return true
            }
            R.id.search -> {
                CURRENT_TYPE = SEARCH
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(view!!, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    private fun initSearchView(menu: Menu?) {
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.isFocusableInTouchMode = true
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                SEARCH_TERM = query
                if (query.isEmpty())
                    viewModel.loadTopRatedMovies()
                viewModel.loadSearchedMovies(query)
                return false
            }
        })
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                when (FORMER_CURRENT_TYPE) {
                    TOP_RATED -> viewModel.loadTopRatedMovies()
                    UPCOMING -> viewModel.loadUpcomingMovies()
                    POPULAR -> viewModel.loadPopularMovies()
                    else -> viewModel.loadPopularMovies()
                }
            }
        }
    }

    private fun initRecyclerView() {
        movie_list_rv.layoutManager = GridLayoutManager(view!!.context, 2, GridLayoutManager.VERTICAL, false)
        viewModel.movieListAdapter.setOnBottomReachedListener(object : OnBottomReachedListener {
            override fun onBottomReached(position: Int, page: Int) {
                viewModel.onNextPage(page + 1, CURRENT_TYPE)
            }
        })
        movie_list_rv.adapter = viewModel.movieListAdapter
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
