package com.stameni.com.movieapp.ui.movies.singleMovie.clips

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.stameni.com.movieapp.R
import com.stameni.com.movieapp.models.movieClips.ClipListResponse
import com.stameni.com.movieapp.util.listen
import kotlinx.android.synthetic.main.clip_item.view.*

class ClipListAdapter : RecyclerView.Adapter<ClipListAdapter.ViewHolder>() {
    private lateinit var clipList: ClipListResponse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.clip_item, parent, false)
        return ViewHolder(v).listen { pos, _ ->
            parent.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${clipList.results[pos].key}")))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateWithUrl("https://i1.ytimg.com/vi/${clipList.results[position].key}/maxresdefault.jpg")
    }

    override fun getItemCount(): Int {
        return if (::clipList.isInitialized) clipList.results.size else 0
    }

    fun setClipList(clipList: ClipListResponse) {
        this.clipList = clipList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun updateWithUrl(url: String) {
            Picasso.get()
                .load(url)
                .fit()
                .centerCrop().into(itemView.myImageView)

        }
    }
}
