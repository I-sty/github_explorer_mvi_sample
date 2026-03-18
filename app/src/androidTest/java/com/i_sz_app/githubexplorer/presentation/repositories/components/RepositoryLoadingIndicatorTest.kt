package com.i_sz_app.githubexplorer.presentation.repositories.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class RepositoryLoadingIndicatorTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `RepositoryLoadingIndicator should display a circular progress indicator`() {
        composeRule.setContent { RepositoryLoadingIndicator() }
        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }
}
