package com.i_sz_app.repositories.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity

@Dao
interface RepositoryMetadataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repositories: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories WHERE `query` = :query")
    fun pagingSource(query: String): PagingSource<Int, RepositoryEntity>

    @Query("DELETE FROM repositories WHERE `query` = :query")
    suspend fun clearByQuery(query: String)
}
