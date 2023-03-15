package com.example.flixster2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class MoviesBaseResponse(
    @SerialName("results")
    val movies: List<Movie>?
)

@Keep
@Serializable
data class Movie (
    @SerialName("title")
    override val title: String,
    @SerialName("overview")
    override val overview: String,
    @SerialName("release_date")
    override val release_date: String,
    @SerialName("vote_average")
    override val rating: String? = null,
    @SerialName("poster_path")
    private val poster_path: String? = null,
) : Media, java.io.Serializable {
    override val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500$poster_path"
}
