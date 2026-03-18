package com.i_sz_app.githubexplorer.data.remote.datasource

import android.util.Log
import com.i_sz_app.githubexplorer.core.constants.NetworkConstants
import com.i_sz_app.githubexplorer.data.remote.api.RepositoryApiService
import com.i_sz_app.githubexplorer.data.remote.dto.SearchResponseDTO
import com.i_sz_app.githubexplorer.domain.exception.GithubException
import org.koin.core.annotation.Single
import retrofit2.HttpException
import java.io.IOException

private val GITHUB_HEADER =
    mapOf(NetworkConstants.GITHUB_ACCEPT_HEADER_KEY to NetworkConstants.GITHUB_ACCEPT_HEADER_VALUE)

@Single
class RepositoryDataSourceImpl(private val api: RepositoryApiService) : RepositoryRemoteDataSource {
    override suspend fun searchRepositories(
        query: String, sort: String, page: Int, perPage: Int
    ): SearchResponseDTO = execute {
        api.searchRepositories(GITHUB_HEADER, query, sort, page, perPage)
    }

    @Throws(GithubException::class)
    private suspend fun <T> execute(call: suspend () -> T): T {
        return try {
            call()
        } catch (e: HttpException) {
            Log.e("RepositoryDataSourceImpl", "HttpException: ${e.message}")
            throw when (e.code()) {
                304 -> GithubException.NotModified()
                403 -> GithubException.RateLimitExceeded()
                404 -> GithubException.NotFound()
                422 -> GithubException.ValidationFailed()
                503 -> GithubException.ServiceUnavailable()
                else -> GithubException.Unknown(e)
            }
        } catch (e: IOException) {
            throw GithubException.NoNetwork(e)
        }
    }
}
