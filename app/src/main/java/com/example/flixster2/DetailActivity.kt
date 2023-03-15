package com.example.flixster2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity/"
class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var releaseTextView: TextView
    private lateinit var ratingTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        overviewTextView = findViewById(R.id.mediaOverview)
        releaseTextView = findViewById(R.id.mediaRelease)
        ratingTextView = findViewById(R.id.mediaRating)

        // Get the extra from the Intent
        val media = intent.getSerializableExtra(MEDIA_EXTRA) as Media

        // Set the media info
        titleTextView.text = media.title
        overviewTextView.text = media.overview
        releaseTextView.text = "Released: ${media.release_date}"
        ratingTextView.text = "Rating: ${media.rating}"

        // Load the media image
        Glide.with(this)
            .load(media.posterUrl)
            .centerInside()
            .into(mediaImageView)
    }
}