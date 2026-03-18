package com.i_sz_app.githubexplorer.data.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.i_sz_app.githubexplorer.data.local.database.AppDatabase
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryRemoteKeyEntity
import org.koin.core.annotation.Single

@Single
class RepositoryLocalDataSourceImpl(
    private val database: AppDatabase
) : RepositoryLocalDataSource {
    override fun pagingSource(query: String): PagingSource<Int, RepositoryEntity> {
        return database.repositoryMetadataDao().pagingSource(query)
    }

    override suspend fun insertRepositories(repos: List<RepositoryEntity>) {
        database.repositoryMetadataDao().insertAll(repos)
    }

    override suspend fun clearRepositoriesByQuery(query: String) {
        database.repositoryMetadataDao().clearByQuery(query)
    }

    override suspend fun insertRemoteKeys(keys: List<RepositoryRemoteKeyEntity>) {
        database.remoteKeyDao().insertAll(keys)
    }

    override suspend fun remoteKeyByRepoId(
        repoId: Long,
        query: String,
    ): RepositoryRemoteKeyEntity? {
        return database.remoteKeyDao().remoteKeyByRepoId(repoId, query)
    }

    override suspend fun clearRemoteKeysByQuery(query: String) {
        database.remoteKeyDao().clearByQuery(query)
    }

    override suspend fun <R> withTransaction(block: suspend () -> R): R =
        database.withTransaction(block)

}
