package com.i_sz_app.githubexplorer.domain.repository

import androidx.paging.PagingData
import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun searchRepositories(query: String, sort: String): Flow<PagingData<RepositoryDetailsModel>>
}
