package com.example.xkcdroid.data

import com.example.xkcdroid.data.model.Comic
import com.example.xkcdroid.data.remote.XkcdApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepository @Inject constructor(
    private val apiService: XkcdApiService
) {

    suspend fun getLatestComic(): Result<Comic> = runCatching {
        apiService.getLatestComic()
    }

    suspend fun getComicById(id: Int): Result<Comic> = runCatching {
        apiService.getComicById(id)
    }
}