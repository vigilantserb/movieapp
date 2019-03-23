package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.stameni.com.movieapp.R

class ActorsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_actors, container, false)
    }
}