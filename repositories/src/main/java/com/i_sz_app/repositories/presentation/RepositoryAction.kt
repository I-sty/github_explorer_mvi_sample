package com.i_sz_app.repositories.presentation

sealed interface RepositoryAction {
    data object LoadContent : RepositoryAction
    data object RefreshContent : RepositoryAction
    data class SearchByLanguage(val language: String) : RepositoryAction
}
