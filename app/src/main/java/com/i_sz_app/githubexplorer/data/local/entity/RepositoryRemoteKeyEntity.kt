package com.i_sz_app.githubexplorer.data.local.entity

import androidx.room.Entity

@Entity(tableName = "remote_keys", primaryKeys = ["repoId", "query"])
data class RepositoryRemoteKeyEntity(
    val repoId: Long,
    val query: String,
    val prevPage: Int?,
    val nextPage: Int?,
)
