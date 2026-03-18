package com.i_sz_app.core.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetailsModel(
    val id: Long,
    val createdAt: String,
    val defaultBranchName: String,
    val description: String?,
    val forksCount: Int,
    val isForked: Boolean,
    val name: String,
    val openIssuesCount: Int,
    val ownerAvatar: String,
    val ownerName: String,
    val size: Int,
    val stargazersCount: Int,
    val updatedAt: String,
    val watchersCount: Int,
)
