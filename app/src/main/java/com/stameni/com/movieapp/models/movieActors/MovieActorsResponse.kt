package com.stameni.com.movieapp.models.movieActors

import com.google.gson.annotations.SerializedName

data class MovieActorsResponse(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)