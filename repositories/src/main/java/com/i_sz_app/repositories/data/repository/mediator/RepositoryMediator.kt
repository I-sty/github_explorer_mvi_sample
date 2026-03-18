package com.i_sz_app.repositories.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.i_sz_app.githubexplorer.data.local.datasource.RepositoryLocalDataSource
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryRemoteKeyEntity
import com.i_sz_app.githubexplorer.data.mapper.toEntity
import com.i_sz_app.githubexplorer.data.remote.datasource.RepositoryRemoteDataSource
import com.i_sz_app.githubexplorer.domain.exception.GithubException
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class RepositoryMediator(
    private val query: String,
    private val sort: String,
    private val localDataSource: RepositoryLocalDataSource,
    private val remoteDataSource: RepositoryRemoteDataSource,
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    STARTING_PAGE
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = false
                    )

                    val remoteKey = localDataSource.remoteKeyByRepoId(lastItem.id, query)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)

                    remoteKey.nextPage
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            val response = remoteDataSource.searchRepositories(
                query = query,
                sort = sort,
                page = page,
                perPage = state.config.pageSize,
            )

            val endOfPaginationReached =
                response.items.isEmpty() || page * state.config.pageSize >= MAX_GITHUB_RESULTS


            localDataSource.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    localDataSource.clearRemoteKeysByQuery(query)
                    localDataSource.clearRepositoriesByQuery(query)

                }

                localDataSource.insertRemoteKeys(
                    response.items.map { dto ->
                        RepositoryRemoteKeyEntity(
                            repoId = dto.id,
                            query = query,
                            prevPage = if (page == STARTING_PAGE) null else page - 1,
                            nextPage = if (endOfPaginationReached) null else page + 1
                        )
                    })
                localDataSource.insertRepositories(
                    response.items.map { it.toEntity(query) })
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: GithubException) {
            MediatorResult.Error(e)
        } catch (e: IOException) {
            MediatorResult.Error(GithubException.NoNetwork(e))
        } catch (e: HttpException) {
            MediatorResult.Error(GithubException.Unknown(e))
        }
    }

    companion object {
        private const val STARTING_PAGE = 1
        private const val MAX_GITHUB_RESULTS = 1000
    }
}
