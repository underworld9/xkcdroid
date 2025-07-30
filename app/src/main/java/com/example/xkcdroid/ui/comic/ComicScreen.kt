package com.example.xkcdroid.ui.comic

import androidx.annotation.StringRes
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.xkcdroid.R
import com.example.xkcdroid.data.model.Comic
import com.example.xkcdroid.ui.comic.components.ComicActionsRow
import com.example.xkcdroid.ui.theme.XkcdroidTheme
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ComicScreen(
    modifier: Modifier = Modifier,
    uiState: ComicScreenState,
    onRetry: () -> Unit,
    onLongPress: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onReadAloudClick: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.error != null -> {
                ErrorView(
                    errorResId = uiState.error,
                    onRetry = onRetry
                )
            }

            uiState.comic != null -> {
                ComicContentView(
                    uiState = uiState,
                    onLongPress = onLongPress,
                    onPreviousClick = onPreviousClick,
                    onNextClick = onNextClick,
                    onFavoriteClick = onFavoriteClick,
                    onReadAloudClick = onReadAloudClick,
                )
            }
        }
    }
}

@Composable
private fun ComicContentView(
    modifier: Modifier = Modifier,
    uiState: ComicScreenState,
    onLongPress: () -> Unit,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onReadAloudClick: () -> Unit,
) {
    val comic = uiState.comic ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = comic.title,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(bottom = 16.dp),
            text = formatDate(
                year = comic.year.toInt(),
                month = comic.month.toInt(),
                dayOfMonth = comic.day.toInt()
            ),
            style = MaterialTheme.typography.bodyMedium
        )

        val zoomState = rememberZoomState()

        SubcomposeAsyncImage(
            model = comic.imageUrl,
            contentDescription = comic.altText,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
                .zoomable(
                    zoomState = zoomState,
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress() },

                    )
                },
            loading = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(32.dp))
                }
            }
        )

        Spacer(modifier = Modifier.height(48.dp))

        ComicActionsRow(
            onPreviousClick = onPreviousClick,
            onNextClick = onNextClick,
            onFavoriteClick = onFavoriteClick,
            onReadAloudClick = onReadAloudClick,
            isPreviousEnabled = uiState.comic.id != 1,
            isNextEnabled = !uiState.isLatestComic
        )
    }
}

@Composable
private fun ErrorView(
    modifier: Modifier = Modifier,
    @StringRes errorResId: Int,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(errorResId),
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(onClick = onRetry) {
            Text(stringResource(id = R.string.action_retry))
        }
    }
}

private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
    return try {
        val date = LocalDate.of(year, month, dayOfMonth)

        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        date.format(formatter)
    } catch (_: Exception) {
        "${dayOfMonth}.${month}.${year}"
    }
}

@Preview(showBackground = true)
@Composable
private fun ComicScreenSuccessPreview() {
    XkcdroidTheme {
        val fakeComic = Comic(
            id = 1,
            title = "A Fake Comic",
            imageUrl = "",
            altText = "Test Comic alt Text",
            transcriptText = "Test Comic Transcript",
            day = "31",
            month = "5",
            year = "2026"
        )

        ComicScreen(
            uiState = ComicScreenState(isLoading = false, comic = fakeComic),
            onRetry = {},
            onLongPress = {},
            onPreviousClick = {},
            onNextClick = {},
            onFavoriteClick = {},
            onReadAloudClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ComicScreenErrorPreview() {
    XkcdroidTheme {
        ComicScreen(
            uiState = ComicScreenState(isLoading = false, error = R.string.error_loading_comic),
            onRetry = {},
            onLongPress = {},
            onPreviousClick = {},
            onNextClick = {},
            onFavoriteClick = {},
            onReadAloudClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ComicScreenLoadingPreview() {
    XkcdroidTheme {
        ComicScreen(
            uiState = ComicScreenState(isLoading = true),
            onRetry = {},
            onLongPress = {},
            onPreviousClick = {},
            onNextClick = {},
            onFavoriteClick = {},
            onReadAloudClick = {},
        )
    }
}
