package com.i_sz_app.githubexplorer.data.remote.datasource

import com.i_sz_app.githubexplorer.data.remote.dto.SearchResponseDTO
import com.i_sz_app.githubexplorer.domain.exception.GithubException

interface RepositoryRemoteDataSource {

    @Throws(GithubException::class)
    suspend fun searchRepositories(
        query: String, sort: String, page: Int, perPage: Int
    ): SearchResponseDTO
}
