package com.i_sz_app.githubexplorer.presentation.repositories.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class RepositoryErrorContentTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `RepositoryErrorContent should display a given error message and a retry button`() {
        composeRule.setContent {
            RepositoryErrorContent(
                message = "Something went wrong",
                onRetry = {})
        }
        composeRule.onNodeWithText("Something went wrong").assertIsDisplayed()
        composeRule.onNodeWithTag("retry_button").assertIsDisplayed()
    }

    @Test
    fun `RepositoryErrorContent should display the fallback error message and a retry button`() {
        composeRule.setContent {
            RepositoryErrorContent(
                message = null,
                onRetry = {})
        }
        composeRule.onNodeWithText("Something went wrong! Try again later.").assertIsDisplayed()
        composeRule.onNodeWithTag("retry_button").assertIsDisplayed()
    }
}
