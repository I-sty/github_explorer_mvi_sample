package com.i_sz_app.repositories.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryRemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keys: List<RepositoryRemoteKeyEntity>)

    @Query("SELECT * FROM remote_keys WHERE repoId = :repoId AND `query` = :query")
    suspend fun remoteKeyByRepoId(repoId: Long, query: String): RepositoryRemoteKeyEntity?

    @Query("DELETE FROM remote_keys WHERE `query` = :query")
    suspend fun clearByQuery(query: String)
}
