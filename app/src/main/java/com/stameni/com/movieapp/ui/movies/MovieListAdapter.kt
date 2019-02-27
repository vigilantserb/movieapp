package com.stameni.com.movieapp.ui.movies

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.models.movieList.ServerResponse
import com.stameni.com.movieapp.ui.movies.singleMovie.MOVIE_ID
import com.stameni.com.movieapp.ui.movies.singleMovie.SingleMovie
import com.stameni.com.movieapp.util.OnBottomReachedListener
import com.stameni.com.movieapp.util.getParentActivity
import com.stameni.com.movieapp.util.listen
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var postList: ServerResponse
    private lateinit var onBottomReachedListener: OnBottomReachedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(v).listen { pos, _ ->
            val title = postList.results[pos].id
            val intent = Intent(v.getParentActivity(), SingleMovie::class.java)
            intent.putExtra(MOVIE_ID, title)
            v.context.startActivity(intent)
        }
    }

    override fun onBindViewHolder(holder: MovieListAdapter.ViewHolder, position: Int) {
        holder.movieTitle.text = postList.results[position].title
        holder.updateWithUrl("https://image.tmdb.org/t/p/w500/${postList.results[position].posterPath}")
        if(position == postList.results.size - 1){
            onBottomReachedListener.onBottomReached(position, postList.page)
        }
    }

    override fun getItemCount(): Int {
        return if (::postList.isInitialized) postList.results.size else 0
    }

    fun setPostList(postList: ServerResponse) {
        this.postList = postList
        notifyDataSetChanged()
    }

    fun updateMovieList(result: ServerResponse?) {
        if (result != null && this.postList.page == result.page - 1){
            for (movie in result.results) {
                this.postList.results.add(movie)
            }
            this.postList.page = result.page
            notifyDataSetChanged()
        }
    }

    fun setOnBottomReachedListener(listener: OnBottomReachedListener?){
        if(listener != null){
            this.onBottomReachedListener = listener
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieTitle = itemView.movie_title

        fun updateWithUrl(url: String) {
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop().into(itemView.myImageView)

        }
    }
}