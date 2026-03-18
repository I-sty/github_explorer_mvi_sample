package com.i_sz_app.githubexplorer.di

import android.content.Context
import androidx.room.Room
import com.i_sz_app.githubexplorer.data.local.database.AppDatabase
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@Configuration
object DatabaseModule {

    @Single
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()
}
