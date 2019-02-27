package com.stameni.com.movieapp.models.movieReviews

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("url")
    val url: String
)