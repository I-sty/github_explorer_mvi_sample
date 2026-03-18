package com.i_sz_app.repositories.presentation.components

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.core.presentation.common.composables.HeaderCard
import com.i_sz_app.repositories.presentation.RepositoryAction

@Composable
fun RepositoryGrid(
    repositories: LazyPagingItems<RepositoryDetailsModel>,
    currentLanguage: String,
    onAction: (RepositoryAction) -> Unit,
    onRepositoryClick: (RepositoryDetailsModel) -> Unit,
    modifier: Modifier = Modifier,
    gridState: LazyStaggeredGridState = rememberLazyStaggeredGridState(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    LazyVerticalStaggeredGrid(
        state = gridState,
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier
            .fillMaxSize()
            .testTag("repository_grid"),
        verticalItemSpacing = 0.dp,
        contentPadding = PaddingValues(bottom = 80.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item(span = StaggeredGridItemSpan.FullLine) {
            LanguageSearchBar(
                language = currentLanguage,
                onLanguageChange = { onAction(RepositoryAction.SearchByLanguage(it)) }
            )
        }

        items(
            count = repositories.itemCount,
            key = { index -> repositories.peek(index)?.id ?: index }
        ) { index ->
            repositories[index]?.let { repository ->
                with(sharedTransitionScope) {
                    HeaderCard(
                        repository = repository,
                        modifier = Modifier
                            .padding(8.dp)
                            .sharedElement(
                                rememberSharedContentState(key = "headerCard-${repository.id}"),
                                animatedVisibilityScope = animatedVisibilityScope
                            ),
                        onRepositoryClick = onRepositoryClick
                    )
                }
            }
        }

        item(span = StaggeredGridItemSpan.FullLine) {
            RepositoryAppendState(
                appendState = repositories.loadState.append,
                onRetry = { repositories.retry() }
            )
        }
    }
}
