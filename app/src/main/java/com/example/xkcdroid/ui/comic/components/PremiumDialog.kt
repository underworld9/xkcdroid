package com.example.xkcdroid.ui.comic.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.xkcdroid.R
import com.example.xkcdroid.ui.theme.XkcdroidTheme

@Composable
fun PremiumDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Default.WorkspacePremium, contentDescription = stringResource(R.string.premium_feature_headline)) },
        title = { Text(text = stringResource(R.string.premium_feature_locked)) },
        text = { Text(text = stringResource(R.string.premium_feature_please_transfer)) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.premium_feature_ask_again))
            }
        }
    )
}

@Preview
@Composable
private fun PremiumFeatureDialogPreview() {
    XkcdroidTheme {
        PremiumDialog(onDismiss = {})
    }
}