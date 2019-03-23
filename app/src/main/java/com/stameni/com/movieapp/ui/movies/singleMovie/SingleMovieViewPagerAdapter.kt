package com.stameni.com.movieapp.ui.movies.singleMovie

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.stameni.com.movieapp.ui.MOVIES_ADAPTER_NUM
import com.stameni.com.movieapp.ui.movies.MoviesFragment
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorsFragment
import com.stameni.com.movieapp.ui.movies.singleMovie.clips.ClipsFragment
import com.stameni.com.movieapp.ui.movies.singleMovie.reviews.ReviewsFragment
import com.stameni.com.movieapp.ui.movies.singleMovie.summary.SummaryFragment
import com.stameni.com.movieapp.ui.tvshow.TvShowsFragment


const val MOVIE_SUMMARY: Int = 0
const val MOVIE_CLIPS: Int = 1
const val MOVIE_ACTORS: Int = 2

class SingleMovieViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            MOVIE_SUMMARY -> SummaryFragment()
            MOVIE_CLIPS -> ClipsFragment()
            MOVIE_ACTORS -> ActorsFragment()
            else -> ReviewsFragment()
        }
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            MOVIE_SUMMARY -> "Summary"
            MOVIE_CLIPS -> "Videos"
            MOVIE_ACTORS -> "Actors"
            else -> "Reviews"
        }
    }
}