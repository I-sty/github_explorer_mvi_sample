package com.i_sz_app.githubexplorer.presentation.repositories.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class LanguageSearchBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `LanguageSearchBar should display the correct placeholder text`() {
        composeRule.setContent {
            LanguageSearchBar(language = "", onLanguageChange = { })
        }
        composeRule.onNodeWithTag("language_search_bar").assertIsDisplayed()
        composeRule.onNodeWithText("Filter by language (e.g. Kotlin, Rust…)").assertIsDisplayed()
    }

    @Test
    fun `LanguageSearchBar should display and update the language correctly`() {
        var captured = ""
        composeRule.setContent {
            LanguageSearchBar(language = "", onLanguageChange = { captured = it })
        }
        composeRule.onNodeWithTag("language_search_bar").assertIsDisplayed()
        composeRule.onNodeWithTag("language_search_bar").performTextInput("kotlin")
        assertEquals("kotlin", captured)
    }

    @Test
    fun `LanguageSearchBar and clear button should display when there is text`() {
        composeRule.setContent {
            LanguageSearchBar(language = "kotlin", onLanguageChange = { })
        }
        composeRule.onNodeWithTag("language_search_bar").assertIsDisplayed()
        composeRule.onNodeWithTag("language_search_bar_clear_button").assertIsDisplayed()
    }

}
