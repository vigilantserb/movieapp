package com.stameni.com.movieapp.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.stameni.com.movieapp.ui.movies.MoviesFragment
import com.stameni.com.movieapp.ui.tvshow.TvShowsFragment

const val MOVIES_ADAPTER_NUM = 0
const val TV_SHOWS_ADAPTER_NUM = 1

class VideoTypeAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            MOVIES_ADAPTER_NUM -> MoviesFragment()
            else -> TvShowsFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            MOVIES_ADAPTER_NUM -> "Movies"
            else -> "TV Shows"
        }
    }
}