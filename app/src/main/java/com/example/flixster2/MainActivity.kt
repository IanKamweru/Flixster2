package com.example.flixster2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.flixster2.databinding.ActivityMainBinding
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val API_KEY = BuildConfig.API_KEY
private const val POPULAR_MOVIES_URL =
    "https://api.themoviedb.org/3/movie/popular?api_key=${API_KEY}&language=en-US"
private const val POPULAR_SHOWS_URL =
    "https://api.themoviedb.org/3/tv/popular?api_key=${API_KEY}&language=en-US"

class MainActivity : AppCompatActivity() {
    private val movies = mutableListOf<Movie>()
    private val shows = mutableListOf<TVShow>()
    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var showsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        moviesRecyclerView = findViewById(R.id.moviesRv)
        showsRecyclerView = findViewById(R.id.showsRv)

        // Set up MovieAdapter with movies
        val movieAdapter = MovieAdapter(this, movies)
        moviesRecyclerView.adapter = movieAdapter
        moviesRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        // Set up ShowsAdapter
        val showAdapter = TVShowAdapter(this, shows)
        showsRecyclerView.adapter = showAdapter
        showsRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        val client = AsyncHttpClient()
        client.get(POPULAR_MOVIES_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch movies: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched movies: $json")
                try {
                    // Create the parsedJSON
                    val parsedJson = createJson().decodeFromString(
                        MoviesBaseResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    // Save the movies
                    parsedJson.movies?.let { list ->
                        movies.addAll(list)
                    }

                    // reload the screen
                    movieAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }
        })

        client.get(POPULAR_SHOWS_URL, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch TV Shows: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched TV Shows: $json")
                try {
                    // Create the parsedJSON
                    val parsedJson = createJson().decodeFromString(
                        TvShowsBaseResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    // Save the movies
                    parsedJson.shows?.let { list ->
                        shows.addAll(list)
                    }

                    // reload the screen
                    showAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })

    }
}