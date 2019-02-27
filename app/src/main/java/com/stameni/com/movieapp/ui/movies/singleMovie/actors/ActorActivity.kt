package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.util.ResizeAnimation
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_actor.*

const val ACTOR_NAME = "actor_name"
const val ACTOR_ID = "actor_id"

class ActorActivity : AppCompatActivity() {
    private var actorName = ""
    private var actorId = 0
    private var isOpen = false

    private lateinit var viewModel: ActorActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actor)

        actorName = intent!!.extras!!.getString(ACTOR_NAME, "")
        actorId = intent!!.extras!!.getInt(ACTOR_ID, 0)

        viewModel = ViewModelProviders.of(this).get(ActorActivityViewModel::class.java)

        viewModel.loadActor(actorId)
        viewModel.loadActorMovies(actorId)

        initActorMoviesRecyclerView()
        loadActorData()
        loadActorMovies()
    }

    private fun initActorMoviesRecyclerView() {
        movie_list_rv.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)
        movie_list_rv.adapter = viewModel.actorMovieListAdapter
    }

    private fun loadActorData() {
        viewModel.singleActor.observe(this, Observer {
            if (it != null) {
                //TODO init all activity fields
                actor_name_tv.text = it.name
                actor_biography_tv.text = it.biography
                actor_birthday_tv.text = it.birthday
                actor_birthtown_tv.text = it.placeOfBirth

                Picasso
                    .get()
                    .load("https://image.tmdb.org/t/p/w500/${it.profilePath}")
                    .into(actor_iv)

                actor_iv.setOnClickListener {
                    zoomActorProfileImage()
                }
            }
        })
    }

    private fun zoomActorProfileImage() {
        if (!isOpen) {
            val resizeAnimation = ResizeAnimation(
                actor_iv,
                700,
                400,
                false
            )
            resizeAnimation.duration = 2000
            actor_iv.startAnimation(resizeAnimation)
            isOpen = !isOpen
        } else {
            val resizeAnimation = ResizeAnimation(
                actor_iv,
                400,
                1100,
                true
            )
            resizeAnimation.duration = 2000
            actor_iv.startAnimation(resizeAnimation)
            isOpen = !isOpen
        }
    }

    private fun loadActorMovies() {
        viewModel.actorMovies.observe(this, Observer {
            if (it != null) {
                it.cast.sortBy { it -> it.voteAverage }
                it.cast.reverse()
                Picasso
                    .get()
                    .load("https://image.tmdb.org/t/p/w500/${it.cast[0].posterPath}")
                    .transform(BlurTransformation(this, 10))
                    .fit()
                    .centerInside()
                    .into(background)
            }
        })
    }
}
