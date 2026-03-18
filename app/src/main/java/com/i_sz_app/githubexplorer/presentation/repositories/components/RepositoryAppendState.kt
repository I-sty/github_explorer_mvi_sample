package com.i_sz_app.githubexplorer.presentation.repositories.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.i_sz_app.githubexplorer.R

@Composable
fun RepositoryAppendState(
    appendState: LoadState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (appendState) {
        is LoadState.Loading -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .testTag("append_loading_indicator")
                )
            }
        }

        is LoadState.Error -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = onRetry,
                    modifier = Modifier.testTag("append_retry_button")
                ) {
                    Text(stringResource(R.string.error_failed_to_load_more))
                }
            }
        }

        else -> Unit
    }
}
