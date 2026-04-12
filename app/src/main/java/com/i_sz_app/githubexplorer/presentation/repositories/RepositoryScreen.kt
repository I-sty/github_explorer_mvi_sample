package com.i_sz_app.githubexplorer.presentation.repositories

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.i_sz_app.githubexplorer.R
import com.i_sz_app.githubexplorer.domain.exception.GithubException
import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.githubexplorer.presentation.common.composables.AppBarTitle
import com.i_sz_app.githubexplorer.presentation.common.theme.GitHubExplorerTheme
import com.i_sz_app.githubexplorer.presentation.repositories.components.RefreshErrorToast
import com.i_sz_app.githubexplorer.presentation.repositories.components.RepositoryErrorContent
import com.i_sz_app.githubexplorer.presentation.repositories.components.RepositoryGrid
import com.i_sz_app.githubexplorer.presentation.repositories.components.RepositoryLoadingIndicator
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RepositoryScreenEntry(
    onRepositoryClick: (RepositoryDetailsModel) -> Unit,
    viewModel: RepositoryViewModel = koinViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val pullToRefreshState = rememberPullToRefreshState()

    RepositoryScreen(
        isRefreshing = state.isRefreshing,
        contentState = state.contentState,
        currentLanguage = state.language,
        pullToRefreshState = pullToRefreshState,
        onAction = viewModel::onAction,
        onRepositoryClick = onRepositoryClick,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedContentScope,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(
    isRefreshing: Boolean,
    contentState: RepositoryContentState,
    currentLanguage: String,
    pullToRefreshState: PullToRefreshState,
    onAction: (RepositoryAction) -> Unit,
    onRepositoryClick: (RepositoryDetailsModel) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    AppBarTitle { innerPadding ->

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { onAction(RepositoryAction.RefreshContent) },
            state = pullToRefreshState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (contentState) {
                RepositoryContentState.Loading -> {
                    RepositoryLoadingIndicator()
                }

                is RepositoryContentState.Error -> {
                    val message = refreshErrorMessage(contentState.throwable)
                    RepositoryErrorContent(message = message, onRetry = {
                        onAction(RepositoryAction.LoadContent)
                    })
                }

                is RepositoryContentState.Content -> {
                    val lazyPagingItems: LazyPagingItems<RepositoryDetailsModel> =
                        contentState.repositories.collectAsLazyPagingItems()

                    RepositoryScreenContent(
                        repositories = lazyPagingItems,
                        currentLanguage,
                        onAction,
                        onRepositoryClick,
                        sharedTransitionScope,
                        animatedVisibilityScope
                    )
                }
            }
        }
    }
}

@Composable
fun RepositoryScreenContent(
    repositories: LazyPagingItems<RepositoryDetailsModel>,
    currentLanguage: String,
    onAction: (RepositoryAction) -> Unit,
    onRepositoryClick: (RepositoryDetailsModel) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    gridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
) {
    val refreshState = repositories.loadState.refresh
    val hasCachedData = repositories.itemCount > 0

    RefreshErrorToast(
        refreshState = refreshState,
        hasCachedData = hasCachedData
    )

    Box(modifier = Modifier.fillMaxSize()) {
        when (refreshState) {
            is LoadState.Loading if !hasCachedData -> {
                RepositoryLoadingIndicator()
            }

            is LoadState.Error if !hasCachedData -> {
                val message = refreshErrorMessage(refreshState.error)
                RepositoryErrorContent(
                    message = message,
                    onRetry = { repositories.retry() }
                )
            }

            else -> {
                RepositoryGrid(
                    repositories = repositories,
                    currentLanguage = currentLanguage,
                    gridState = gridState,
                    onAction = onAction,
                    onRepositoryClick = onRepositoryClick,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }
        }
    }
}

@Composable
private fun refreshErrorMessage(error: Throwable): String {
    val rateLimitMessage = stringResource(R.string.error_rate_limit)
    val noInternetMessage = stringResource(R.string.error_no_network)
    val genericErrorMessage = stringResource(R.string.error_something_went_wrong)

    return when (error) {
        is GithubException.RateLimitExceeded -> rateLimitMessage
        is GithubException.NoNetwork -> noInternetMessage
        is GithubException.Unknown -> error.cause.message ?: genericErrorMessage
        is Throwable -> error.localizedMessage ?: genericErrorMessage
    }
}

@Preview
@Composable
private fun RepositoryScreenLoading_Preview() {
    val isRefreshing = false
    val contentState = RepositoryContentState.Loading
    GitHubExplorerTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                RepositoryScreen(
                    isRefreshing = isRefreshing,
                    contentState = contentState,
                    currentLanguage = "",
                    pullToRefreshState = PullToRefreshState(),
                    onAction = {},
                    onRepositoryClick = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryScreenError_Preview() {
    val isRefreshing = false
    val contentState = RepositoryContentState.Error(Exception("Something went wrong!"))
    GitHubExplorerTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                RepositoryScreen(
                    isRefreshing = isRefreshing,
                    contentState = contentState,
                    currentLanguage = "",
                    pullToRefreshState = PullToRefreshState(),
                    onAction = {},
                    onRepositoryClick = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                )
            }
        }
    }
}

@Preview
@Composable
private fun RepositoryScreenContent_Preview() {
    val isRefreshing = false
    val fakeRepos = List(5) {
        buildRepositoryDetailsModel(
            id = it.toLong(),
            ownerAvatar = "",
            description = when (it % 3) {
                1 -> "Lorem Ipsum is simply dummy text #$it"
                2 -> "Lorem Ipsum is simply dummy text of the printing and typesetting industry. #$it"
                else -> "description #$it"
            },
        )
    }
    val pagingData = PagingData.from(fakeRepos)
    val fakeDataFlow = MutableStateFlow(pagingData)
    val contentState = RepositoryContentState.Content(fakeDataFlow)

    GitHubExplorerTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                RepositoryScreen(
                    isRefreshing = isRefreshing,
                    contentState = contentState,
                    currentLanguage = "",
                    pullToRefreshState = PullToRefreshState(),
                    onAction = {},
                    onRepositoryClick = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                )
            }
        }
    }
}
