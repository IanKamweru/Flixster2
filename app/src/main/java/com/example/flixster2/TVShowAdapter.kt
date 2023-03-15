package com.example.flixster2

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class TVShowAdapter(private val context: Context, private val shows: List<TVShow>) :
    RecyclerView.Adapter<TVShowAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the individual tv show and bind to holder
        val show = shows[position]
        holder.bind(show)
    }

    override fun getItemCount(): Int {
        return shows.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        // get views
        private val posterImageView = itemView.findViewById<ImageView>(R.id.mediaPoster)
        private val titleTextView = itemView.findViewById<TextView>(R.id.mediaTitle)
        //private val overviewTextView = itemView.findViewById<TextView>(R.id.movieOverview)

        init {
            itemView.setOnClickListener(this)
        }

        // helper method to help set up the onBindViewHolder method
        fun bind(show: TVShow) {
            titleTextView.text = show.title
            //overviewTextView.text = movie.overview

            Glide.with(context)
                .load(show.posterUrl)
                .transform(RoundedCorners(20))
                .into(posterImageView)
        }

        override fun onClick(p0: View?) {
            // Get selected show
            val show = shows[absoluteAdapterPosition]

            // Navigate to Details screen and pass selected show
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MEDIA_EXTRA, show)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                p0!!.context as Activity,
                p0, "profile"
            )
            context.startActivity(intent, options.toBundle())
        }
    }
}