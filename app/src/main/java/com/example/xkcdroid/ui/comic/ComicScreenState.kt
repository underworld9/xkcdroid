package com.example.xkcdroid.ui.comic

import androidx.annotation.StringRes
import com.example.xkcdroid.data.model.Comic

data class ComicScreenState(
    val comic: Comic? = null,
    val isLoading: Boolean = false,
    @param:StringRes val error: Int? = null,

    val isLatestComic: Boolean = false,

    val showAltText: Boolean = false,
    val isSearchDialogVisible: Boolean = false,
    val isPremiumDialogVisible: Boolean = false,

    val searchQuery: String = ""
)