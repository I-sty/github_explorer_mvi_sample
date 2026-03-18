package com.i_sz_app.repositories.data.mapper

import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.remote.dto.RepositoryDTO

fun RepositoryEntity.toDomain(): RepositoryDetailsModel {
    // no validation is needed
    // according to the documentation only the descriptio and the ownerAvatar could be null
    // we can show a proper UI without these optional fields
    return RepositoryDetailsModel(
        id = id,
        createdAt = createdAt,
        defaultBranchName = defaultBranchName,
        description = description,
        forksCount = forksCount,
        isForked = isForked,
        name = name,
        openIssuesCount = openIssuesCount,
        ownerAvatar = ownerAvatar,
        ownerName = ownerName,
        size = size,
        stargazersCount = stargazersCount,
        updatedAt = updatedAt,
        watchersCount = watchersCount
    )
}

fun RepositoryDTO.toEntity(query: String) = RepositoryEntity(
    id = id,
    createdAt = createdAt,
    defaultBranchName = defaultBranch,
    description = description,
    forksCount = forksCount,
    isForked = fork,
    name = name,
    openIssuesCount = openIssuesCount,
    ownerAvatar = owner.avatarUrl,
    ownerName = owner.login,
    query = query,
    size = size,
    stargazersCount = stargazersCount,
    updatedAt = updatedAt,
    watchersCount = watchersCount,
)
