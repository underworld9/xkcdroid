package com.example.xkcdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.xkcdroid.ui.comic.components.ComicAppBar
import com.example.xkcdroid.ui.comic.ComicScreen
import com.example.xkcdroid.ui.comic.ComicViewModel
import com.example.xkcdroid.ui.comic.components.PremiumDialog
import com.example.xkcdroid.ui.comic.components.SearchDialog
import com.example.xkcdroid.ui.theme.XkcdroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            XkcdroidTheme {
                val viewModel: ComicViewModel = hiltViewModel()
                val uiState by viewModel.uiState.collectAsState()

                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.showAltText) {
                    if (uiState.showAltText) {
                        val altText = uiState.comic?.altText ?: ""
                        snackbarHostState.showSnackbar(message = altText)
                        viewModel.onAltTextShown()
                    }
                }

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    topBar = {
                        ComicAppBar(
                            comicNumber = uiState.comic?.id,
                            onRandomClick = viewModel::loadRandomComic,
                            onLatestClick = viewModel::loadLatestComic,
                            onSearchClick = viewModel::onSearchClick,
                            isLatestEnabled = !uiState.isLatestComic
                        )
                    },
                ) { innerPadding ->
                    ComicScreen(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        onRetry = viewModel::onRetry,
                        onLongPress = viewModel::onComicLongPress,
                        onPreviousClick = viewModel::loadPreviousComic,
                        onNextClick = viewModel::loadNextComic,
                        onFavoriteClick = viewModel::onPremiumFeatureClick,
                        onReadAloudClick = viewModel::onPremiumFeatureClick,
                    )
                }

                if (uiState.isSearchDialogVisible) {
                    SearchDialog(
                        value = uiState.searchQuery,
                        onValueChange = viewModel::onSearchQueryChange,
                        onDismiss = viewModel::onSearchDismiss,
                        onConfirm = viewModel::onSearchConfirm
                    )
                }

                if (uiState.isPremiumDialogVisible) {
                    PremiumDialog (
                        onDismiss = viewModel::onPremiumDialogDismiss,
                    )
                }
            }
        }
    }
}
