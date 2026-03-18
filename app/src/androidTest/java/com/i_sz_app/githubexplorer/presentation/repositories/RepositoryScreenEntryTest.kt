package com.i_sz_app.githubexplorer.presentation.repositories

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.i_sz_app.githubexplorer.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.githubexplorer.presentation.repositories.components.LanguageSearchBar
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class RepositoryScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `loading state shows circular progress indicator`() {
        composeRule.setContent {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RepositoryScreen(
                        isRefreshing = false,
                        contentState = RepositoryContentState.Loading,
                        currentLanguage = "",
                        onAction = {},
                        onRepositoryClick = {},
                        pullToRefreshState = rememberPullToRefreshState(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility
                    )
                }
            }
        }

        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun `error state shows error message`() {
        composeRule.setContent {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RepositoryScreen(
                        isRefreshing = false,
                        contentState = RepositoryContentState.Error(Throwable("custom error message")),
                        currentLanguage = "",
                        onAction = {},
                        onRepositoryClick = {},
                        pullToRefreshState = rememberPullToRefreshState(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility
                    )
                }
            }
        }

        composeRule.onNodeWithTag("retry_button").assertIsDisplayed()
        composeRule.onNodeWithText("custom error message").assertIsDisplayed()
    }

    @Test
    fun `content state with empty data shows loading indicator`() {
        composeRule.setContent {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RepositoryScreen(
                        isRefreshing = false,
                        contentState = RepositoryContentState.Content(
                            repositories = flowOf(PagingData.empty())
                        ),
                        currentLanguage = "",
                        onAction = {},
                        onRepositoryClick = {},
                        pullToRefreshState = rememberPullToRefreshState(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility,
                    )
                }
            }
        }

        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
        composeRule.onNodeWithTag("append_loading_indicator").assertDoesNotExist()
    }


    @Test
    fun `content state with data list shows grid`() {
        composeRule.setContent {
            SharedTransitionLayout {
                AnimatedVisibility(visible = true) {
                    RepositoryScreen(
                        isRefreshing = false,
                        contentState = RepositoryContentState.Content(
                            repositories = flowOf(
                                PagingData.from(
                                    data = List(5) { buildRepositoryDetailsModel(id = it.toLong()) },
                                    sourceLoadStates = LoadStates(
                                        refresh = LoadState.NotLoading(endOfPaginationReached = false),
                                        prepend = LoadState.NotLoading(endOfPaginationReached = true),
                                        append = LoadState.NotLoading(endOfPaginationReached = false)
                                    )
                                )
                            )
                        ),
                        currentLanguage = "",
                        onAction = {},
                        onRepositoryClick = {},
                        pullToRefreshState = rememberPullToRefreshState(),
                        sharedTransitionScope = this@SharedTransitionLayout,
                        animatedVisibilityScope = this@AnimatedVisibility,
                    )
                }
            }
        }

        composeRule.onNodeWithTag("repository_grid").assertIsDisplayed()
        composeRule.onNodeWithTag("loading_indicator").assertDoesNotExist()
        composeRule.onNodeWithTag("append_loading_indicator").assertDoesNotExist()

    }

    @Test
    fun `language search bar triggers SearchByLanguage action`() {
        var capturedAction: RepositoryAction? = null

        composeRule.setContent {
            LanguageSearchBar(
                language = "",
                onLanguageChange = { capturedAction = RepositoryAction.SearchByLanguage(it) }
            )
        }

        composeRule.onNodeWithTag("language_search_bar").performTextInput("kotlin")
        assertEquals(RepositoryAction.SearchByLanguage("kotlin"), capturedAction)
    }
}
