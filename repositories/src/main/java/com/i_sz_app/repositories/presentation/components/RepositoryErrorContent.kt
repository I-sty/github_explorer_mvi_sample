package com.i_sz_app.repositories.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.i_sz_app.core.presentation.common.theme.GitHubExplorerTheme
import com.i_sz_app.repositories.R

@Composable
fun RepositoryErrorContent(
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        Text(
            text = message ?: stringResource(R.string.error_something_went_wrong),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 18.sp,
        )
        TextButton(
            onClick = onRetry,
            modifier = Modifier.testTag("retry_button"),
        ) {
            Text(
                text = stringResource(R.string.retry),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RepositoryErrorContent_Preview() {
    GitHubExplorerTheme {
        RepositoryErrorContent(stringResource(R.string.error_no_network), {})
    }
}
