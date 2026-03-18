package com.i_sz_app.repositories.domain.repository

import androidx.paging.PagingData
import com.i_sz_app.repositories.domain.model.RepositoryDetailsModel
import kotlinx.coroutines.flow.Flow

interface IRepository {
    fun searchRepositories(query: String, sort: String): Flow<PagingData<RepositoryDetailsModel>>
}
