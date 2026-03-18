package com.i_sz_app.githubexplorer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDTO(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("incomplete_results") val incompleteResults: Boolean,
    val items: List<RepositoryDTO>
)
