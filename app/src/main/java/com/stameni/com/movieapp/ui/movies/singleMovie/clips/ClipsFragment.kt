package com.stameni.com.movieapp.ui.movies.singleMovie.clips

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.stameni.com.movieapp.R

class ClipsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_clips, container, false)
    }
}