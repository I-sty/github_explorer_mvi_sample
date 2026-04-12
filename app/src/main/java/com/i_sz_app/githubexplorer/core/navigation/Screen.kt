package com.i_sz_app.githubexplorer.core.navigation

import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data class Details(val repositoryDetails: RepositoryDetailsModel)

    @Serializable
    data object Repositories
}
