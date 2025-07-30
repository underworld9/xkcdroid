package com.example.xkcdroid.data.model

import com.google.gson.annotations.SerializedName

data class Comic(
    @SerializedName("num")
    val id: Int,

    @SerializedName("safe_title")
    val title: String,

    @SerializedName("img")
    val imageUrl: String,

    @SerializedName("alt")
    val altText: String,

    @SerializedName("transcript")
    val transcriptText: String,

    val day: String,
    val month: String,
    val year: String
)