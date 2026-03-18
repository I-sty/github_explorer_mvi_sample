package com.i_sz_app.githubexplorer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity (
    @PrimaryKey
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
    val query: String
)
