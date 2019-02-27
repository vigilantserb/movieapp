package com.stameni.com.movieapp.models.movieImages

import com.google.gson.annotations.SerializedName

data class MovieImagesResponse(
    @SerializedName("backdrops")
    var backdrops: ArrayList<Backdrop>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("posters")
    val posters: List<Poster>
)