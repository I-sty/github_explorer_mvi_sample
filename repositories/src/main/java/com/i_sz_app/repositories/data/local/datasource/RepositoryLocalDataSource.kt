package com.i_sz_app.repositories.data.local.datasource

import androidx.paging.PagingSource
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryRemoteKeyEntity

interface RepositoryLocalDataSource {
    fun pagingSource(query: String): PagingSource<Int, RepositoryEntity>
    suspend fun insertRepositories(repos: List<RepositoryEntity>)
    suspend fun clearRepositoriesByQuery(query: String)
    suspend fun insertRemoteKeys(keys: List<RepositoryRemoteKeyEntity>)
    suspend fun remoteKeyByRepoId(repoId: Long, query: String): RepositoryRemoteKeyEntity?
    suspend fun clearRemoteKeysByQuery(query: String)
    suspend fun <R> withTransaction(block: suspend () -> R): R
}
