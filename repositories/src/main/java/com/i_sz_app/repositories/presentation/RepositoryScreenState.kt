package com.i_sz_app.repositories.presentation

import androidx.paging.PagingData
import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.model.RepositorySort
import kotlinx.coroutines.flow.Flow

data class RepositoryScreenState(
    val contentState: RepositoryContentState = RepositoryContentState.Loading,
    val isRefreshing: Boolean = false,
    val language: String = "",
    val sort: RepositorySort = RepositorySort.Stars,
)

sealed interface RepositoryContentState {
    data object Loading : RepositoryContentState
    data class Content(val repositories: Flow<PagingData<RepositoryDetailsModel>>) :
        RepositoryContentState

    data class Error(val throwable: Throwable) : RepositoryContentState
}
