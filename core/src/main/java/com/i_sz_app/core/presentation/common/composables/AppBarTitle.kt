package com.i_sz_app.core.presentation.common.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.i_sz_app.core.R
import com.i_sz_app.core.presentation.common.theme.GitHubExplorerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarTitle(
    modifier: Modifier = Modifier,
    screenTitle: String = stringResource(R.string.screen_repositories),
    onNavigateBack: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = screenTitle,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    onNavigateBack?.let {
                        IconButton(onClick = { it() }) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_back),
                                contentDescription = stringResource(R.string.content_desc_back)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Preview
@Composable
private fun AppBarTitle_Preview() {
    GitHubExplorerTheme {
        AppBarTitle(screenTitle = "Long Verbose Screen Title. Long Verbose Screen Title") { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) { Text("screen content") }
        }
    }
}
