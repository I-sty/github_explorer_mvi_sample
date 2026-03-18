package com.i_sz_app.repositories.data.remote.api

import com.i_sz_app.githubexplorer.data.remote.dto.SearchResponseDTO
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

interface RepositoryApiService {

    // https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories
    @GET("/search/repositories")
    suspend fun searchRepositories(
        @HeaderMap headers: Map<String, String>,
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResponseDTO
}
