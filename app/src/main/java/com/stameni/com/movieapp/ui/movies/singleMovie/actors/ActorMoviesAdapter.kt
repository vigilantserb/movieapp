package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.models.actorMovies.ActorMoviesResponse
import com.stameni.com.movieapp.ui.movies.singleMovie.MOVIE_ID
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovie
import com.stameni.com.movieapp.ui.movies.singleMovie.actors.ActorMoviesAdapter.ViewHolder
import com.stameni.com.movieapp.util.getParentActivity
import com.stameni.com.movieapp.util.listen
import kotlinx.android.synthetic.main.actor_movie_item.view.*


class ActorMoviesAdapter : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var actorMovieList: ActorMoviesResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.actor_movie_item, parent, false)
        return ViewHolder(v).listen { pos, type ->
            val id = actorMovieList.cast[pos].id
            val intent = Intent(v.getParentActivity(), SingleMovie::class.java)
            intent.putExtra(MOVIE_ID, id)
            v.context.startActivity(intent)
        }
    }

    override fun onBindViewHolder(holder: ActorMoviesAdapter.ViewHolder, position: Int) {
        holder.updateWithUrl("https://image.tmdb.org/t/p/w500/${actorMovieList.cast[position].posterPath}")
    }

    override fun getItemCount(): Int {
        return if (::actorMovieList.isInitialized) actorMovieList.cast.size else 0
    }

    fun setActorMoviesList(actorMovieList: ActorMoviesResponse) {
        this.actorMovieList = actorMovieList
        actorMovieList.cast.sortBy { it -> it.voteAverage }
        actorMovieList.cast.reverse()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun updateWithUrl(url: String) {
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(itemView.myImageView)
        }
    }

}
