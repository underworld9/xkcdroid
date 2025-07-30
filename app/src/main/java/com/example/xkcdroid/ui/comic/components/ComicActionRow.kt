package com.example.xkcdroid.ui.comic.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.xkcdroid.R
import com.example.xkcdroid.ui.theme.XkcdroidTheme

@Composable
fun ComicActionsRow(
    modifier: Modifier = Modifier,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onReadAloudClick: () -> Unit,
    isPreviousEnabled: Boolean,
    isNextEnabled: Boolean,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick, enabled = isPreviousEnabled) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.previous_comic)
            )
        }

        IconButton(onClick = onFavoriteClick) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.favorite),
                tint = LocalContentColor.current
            )
        }

        IconButton(onClick = onReadAloudClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                contentDescription = stringResource(R.string.read_aloud)
            )
        }

        IconButton(onClick = onNextClick, enabled = isNextEnabled) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.next_comic)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ComicActionsRowPreview() {
    XkcdroidTheme {
        ComicActionsRow(
            onPreviousClick = {},
            onNextClick = {},
            onFavoriteClick = {},
            onReadAloudClick = {},
            isPreviousEnabled = true,
            isNextEnabled = true,
        )
    }
}