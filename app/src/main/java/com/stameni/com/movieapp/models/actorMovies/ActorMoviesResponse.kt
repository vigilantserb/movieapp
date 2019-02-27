package com.stameni.com.movieapp.models.actorMovies

import com.google.gson.annotations.SerializedName

data class ActorMoviesResponse(
    @SerializedName("cast")
    val cast: ArrayList<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)