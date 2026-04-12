package com.i_sz_app.githubexplorer.presentation.repositories

sealed interface RepositoryAction {
    data object LoadContent : RepositoryAction
    data object RefreshContent : RepositoryAction
    data class SearchByLanguage(val language: String) : RepositoryAction
}
