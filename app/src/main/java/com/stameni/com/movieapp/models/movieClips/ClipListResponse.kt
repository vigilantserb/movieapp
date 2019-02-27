package com.stameni.com.movieapp.models.movieClips

import com.google.gson.annotations.SerializedName

data class ClipListResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<Result>
)