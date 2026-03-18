package com.i_sz_app.githubexplorer.presentation.main.navigation

import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data class Details(val repositoryDetails: RepositoryDetailsModel)

    @Serializable
    data object Repositories
}
