package com.i_sz_app.githubexplorer.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.i_sz_app.githubexplorer.data.local.dao.RemoteKeyDao
import com.i_sz_app.githubexplorer.data.local.dao.RepositoryMetadataDao
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryRemoteKeyEntity

@Database(
    entities = [RepositoryEntity::class, RepositoryRemoteKeyEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME: String = "app_database"
    }

    abstract fun repositoryMetadataDao(): RepositoryMetadataDao

    abstract fun remoteKeyDao(): RemoteKeyDao
}
