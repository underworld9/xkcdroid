package com.example.xkcdroid.ui.comic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xkcdroid.R
import com.example.xkcdroid.data.ComicRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicViewModel @Inject constructor(
    private val repository: ComicRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ComicScreenState())
    val uiState: StateFlow<ComicScreenState> = _uiState.asStateFlow()

    private var latestComicNumber: Int? = null
    private var lastRequestedId: Int? = null

    init {
        loadLatestComic()
    }

    fun loadLatestComic() {
        lastRequestedId = null

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getLatestComic()
                .onSuccess { comic ->
                    latestComicNumber = comic.id

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            comic = comic,
                            error = null,
                            isLatestComic = true
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = R.string.error_loading_comic,
                        )
                    }
                }
        }
    }

    fun loadComicById(id: Int) {
        lastRequestedId = id

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            repository.getComicById(id)
                .onSuccess { comic ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            comic = comic,
                            error = null,
                            isLatestComic = comic.id == latestComicNumber
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = R.string.error_loading_comic
                        )
                    }
                }
        }
    }


    fun loadRandomComic() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val maxComicNumber = latestComicNumber ?: repository.getLatestComic().getOrNull()?.id

            if (maxComicNumber != null) {
                val randomId = (1..maxComicNumber).random()
                loadComicById(randomId)
            }
        }
    }

    fun loadNextComic() {
        val currentId = _uiState.value.comic?.id ?: return

        if (currentId < (latestComicNumber ?: Int.MAX_VALUE)) {
            val nextId = currentId + 1
            loadComicById(nextId)
        }
    }

    fun loadPreviousComic() {
        val currentId = _uiState.value.comic?.id ?: return

        if (currentId > 1) {
            val previousId = currentId - 1
            loadComicById(previousId)
        }
    }

    fun onComicLongPress() {
        if (!_uiState.value.comic?.altText.isNullOrBlank()) {
            _uiState.update { it.copy(showAltText = true) }
        }
    }

    fun onAltTextShown() {
        _uiState.update { it.copy(showAltText = false) }
    }

    fun onRetry() {
        if (lastRequestedId == null) {
            loadLatestComic()
        } else {
            loadComicById(lastRequestedId!!)
        }
    }

    fun onSearchClick() {
        _uiState.update { it.copy(isSearchDialogVisible = true, searchQuery = "") }
    }

    fun onSearchQueryChange(query: String) {
        if (query.all { it.isDigit() } && query.length <= 5) {
            _uiState.update { it.copy(searchQuery = query) }
        }
    }

    fun onSearchDismiss() {
        _uiState.update { it.copy(isSearchDialogVisible = false) }
    }

    fun onSearchConfirm() {
        val query = _uiState.value.searchQuery
        val maxComic = latestComicNumber

        if (query.isNotBlank()) {
            val comicId = query.toInt()
            if (maxComic != null && comicId in 1..maxComic) {
                loadComicById(comicId)
                onSearchDismiss()
            } else {
                loadLatestComic()
                onSearchDismiss()
            }
        }
    }

    fun onPremiumFeatureClick() {
        _uiState.update { it.copy(isPremiumDialogVisible = true) }
    }

    fun onPremiumDialogDismiss() {
        _uiState.update { it.copy(isPremiumDialogVisible = false) }
    }
}