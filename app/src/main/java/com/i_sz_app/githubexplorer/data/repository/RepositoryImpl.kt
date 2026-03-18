package com.i_sz_app.githubexplorer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.i_sz_app.githubexplorer.data.local.datasource.RepositoryLocalDataSource
import com.i_sz_app.githubexplorer.data.mapper.toDomain
import com.i_sz_app.githubexplorer.data.remote.datasource.RepositoryRemoteDataSource
import com.i_sz_app.githubexplorer.data.repository.mediator.RepositoryMediator
import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@OptIn(ExperimentalPagingApi::class)
@Factory
class RepositoryImpl(
    private val remoteDataSource: RepositoryRemoteDataSource,
    private val localDataSource: RepositoryLocalDataSource,
) : IRepository {
    override fun searchRepositories(
        query: String,
        sort: String,
    ): Flow<PagingData<RepositoryDetailsModel>> = Pager(
        config = PagingConfig(
            pageSize = PAGING_CONFIG_PAGE_SIZE,
            prefetchDistance = PAGING_CONFIG_PREFETCH_DISTANCE,
            initialLoadSize = PAGING_CONFIG_PAGE_SIZE,
            enablePlaceholders = PAGING_CONFIG_ENABLE_PLACEHOLDERS,
        ),
        remoteMediator = RepositoryMediator(
            query = query,
            sort = sort,
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        ),
        pagingSourceFactory = { localDataSource.pagingSource(query) })
        .flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }

    companion object {
        const val PAGING_CONFIG_PAGE_SIZE = 50
        const val PAGING_CONFIG_PREFETCH_DISTANCE = 8
        const val PAGING_CONFIG_ENABLE_PLACEHOLDERS = false
    }
}
