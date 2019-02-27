package com.stameni.com.movieapp.ui.movies.singleMovie.reviews

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.models.movieReviews.MovieReviewResponse
import com.stameni.com.movieapp.util.listen
import kotlinx.android.synthetic.main.movie_review_item.view.*

class MovieReviewsAdapter : RecyclerView.Adapter<MovieReviewsAdapter.ViewHolder>() {
    private lateinit var movieReviewList: MovieReviewResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.movie_review_item, parent, false)
        return ViewHolder(v).listen { pos, type ->
            println("Press me daddy")
        }
    }

    override fun getItemCount(): Int {
        return if (::movieReviewList.isInitialized) movieReviewList.results.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieReviewer.text = movieReviewList.results[position].author
        holder.movieReview.text = movieReviewList.results[position].content
    }

    fun setReviewList(result: MovieReviewResponse) {
        this.movieReviewList = result
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieReviewer = itemView.movie_reviewer_tv
        var movieReview = itemView.movie_review_tv

    }
}