package com.example.xkcdroid.data.remote

import com.example.xkcdroid.data.model.Comic
import retrofit2.http.GET
import retrofit2.http.Path

interface XkcdApiService {

    /**
     * Fetches the most recent xkcd comic.
     */
    @GET("info.0.json")
    suspend fun getLatestComic(): Comic

    /**
     * @param comicId the number of the comic to fetch.
     */
    @GET("{comicId}/info.0.json")
    suspend fun getComicById(@Path("comicId") comicId: Int): Comic
}