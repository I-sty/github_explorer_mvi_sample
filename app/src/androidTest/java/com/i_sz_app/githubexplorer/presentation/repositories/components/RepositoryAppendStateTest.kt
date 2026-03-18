package com.i_sz_app.githubexplorer.presentation.repositories.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class RepositoryAppendStateTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `RepositoryAppendState should display a loading indicator when appendState is Loading`() {
        composeRule.setContent {
            RepositoryAppendState(appendState = LoadState.Loading, onRetry = {})
        }
        composeRule.onNodeWithTag("append_loading_indicator").assertIsDisplayed()
    }

    @Test
    fun `RepositoryAppendState should display a retry button when appendState is Error`() {
        var retried = false
        composeRule.setContent {
            RepositoryAppendState(
                appendState = LoadState.Error(Exception()),
                onRetry = { retried = true }
            )
        }
        composeRule.onNodeWithTag("append_retry_button").assertIsDisplayed()
        composeRule.onNodeWithTag("append_retry_button").performClick()
        assertTrue(retried)
    }
}
