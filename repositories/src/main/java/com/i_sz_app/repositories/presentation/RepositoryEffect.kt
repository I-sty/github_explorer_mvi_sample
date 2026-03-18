package com.i_sz_app.repositories.presentation

sealed interface RepositoryEffect {
    data class GenericError(val message: String?) : RepositoryEffect
}
