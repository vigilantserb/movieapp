package com.stameni.com.movieapp.ui.movies.singleMovie.actors

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.models.movieActors.MovieActorsResponse
import com.stameni.com.movieapp.util.getParentActivity
import com.stameni.com.movieapp.util.listen
import kotlinx.android.synthetic.main.actor_item.view.*


class ActorListAdapter : RecyclerView.Adapter<ActorListAdapter.ViewHolder>() {
    private lateinit var actorList: MovieActorsResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.actor_item, parent, false)
        return ViewHolder(v).listen { pos, type ->
            val intent = Intent(v.getParentActivity(), ActorActivity::class.java)
            intent.putExtra(ACTOR_ID, actorList.cast[pos].id)
            intent.putExtra(ACTOR_NAME, actorList.cast[pos].name)
            v.context.startActivity(intent)
        }
    }

    override fun onBindViewHolder(holder: ActorListAdapter.ViewHolder, position: Int) {
        holder.updateWithUrl("https://image.tmdb.org/t/p/w500/${actorList.cast[position].profilePath}")
        holder.actorName.text = actorList.cast[position].name
    }

    override fun getItemCount(): Int {
        return if (::actorList.isInitialized) actorList.cast.size else 0
    }

    fun setActorList(actorList: MovieActorsResponse) {
        this.actorList = actorList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var actorName = itemView.actor_name_tv
        fun updateWithUrl(url: String) {
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop().into(itemView.myImageView)
        }
    }
}
