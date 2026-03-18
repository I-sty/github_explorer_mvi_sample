package com.i_sz_app.githubexplorer.presentation.repositories

sealed interface RepositoryEffect {
    data class GenericError(val message: String?) : RepositoryEffect
}
