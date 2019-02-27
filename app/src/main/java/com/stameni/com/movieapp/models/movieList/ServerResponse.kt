package com.stameni.com.movieapp.models.movieList

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    @SerializedName("page")
    var page: Int,
    @SerializedName("results")
    var results: MutableList<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)