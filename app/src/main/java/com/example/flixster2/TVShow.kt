package com.example.flixster2

import android.support.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class TvShowsBaseResponse(
    @SerialName("results")
    val shows: List<TVShow>?
)

@Keep
@Serializable
data class TVShow (
    @SerialName("name")
    override val title: String,
    @SerialName("overview")
    override val overview: String? = null,
    @SerialName("first_air_date")
    override val release_date: String? = null,
    @SerialName("vote_average")
    override val rating: String? = null,
    @SerialName("poster_path")
    private val poster_path: String? = null,
) : Media, java.io.Serializable {
    override val posterUrl: String
        get() = "https://image.tmdb.org/t/p/w500$poster_path"
}