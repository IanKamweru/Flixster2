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

const val MEDIA_EXTRA = "MEDIA_EXTRA"

class MovieAdapter(private val context: Context, private val movies: List<Movie>) :
RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Get the individual movie and bind to holder
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
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
        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            //overviewTextView.text = movie.overview

            Glide.with(context)
                .load(movie.posterUrl)
                .transform(RoundedCorners(20))
                .into(posterImageView)
        }

        override fun onClick(p0: View?) {
            // Get selected movie
            val movie = movies[absoluteAdapterPosition]

            // Navigate to Details screen and pass selected movie
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MEDIA_EXTRA, movie)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                p0!!.context as Activity,
                p0, "profile"
            )
            context.startActivity(intent, options.toBundle())
        }
    }
}