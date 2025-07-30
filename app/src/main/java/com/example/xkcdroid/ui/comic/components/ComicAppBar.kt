package com.example.xkcdroid.ui.comic.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.xkcdroid.R
import com.example.xkcdroid.ui.theme.XkcdroidTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicAppBar(
    modifier: Modifier = Modifier,
    comicNumber: Int?,
    onRandomClick: () -> Unit,
    onLatestClick: () -> Unit,
    onSearchClick: () -> Unit,
    isLatestEnabled: Boolean
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            val titleText = if (comicNumber != null) {
                "xkcd #$comicNumber"
            } else {
                stringResource(id = R.string.app_name)
            }
            Text(
                text = titleText,
                modifier = Modifier.testTag("TopAppBarTitle")
            )
        },
        navigationIcon = {
            IconButton(onClick = onRandomClick) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = stringResource(R.string.random_comic)
                )
            }
        },
        actions = {
            IconButton(onClick = onLatestClick, enabled = isLatestEnabled) {
                Icon(
                    imageVector = Icons.Default.Today,
                    contentDescription = stringResource(R.string.latest_comic)
                )
            }
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
        }
    )
}

@Preview
@Composable
private fun ComicAppBarPreview() {
    XkcdroidTheme {
        ComicAppBar(
            comicNumber = 987,
            onRandomClick = {},
            onLatestClick = {},
            onSearchClick = {},
            isLatestEnabled = true
        )
    }
}