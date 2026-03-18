package com.i_sz_app.githubexplorer.di

import com.i_sz_app.githubexplorer.core.constants.NetworkConstants
import com.i_sz_app.githubexplorer.data.remote.api.RepositoryApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.annotation.Configuration
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.time.Duration.Companion.seconds

@Module
@Configuration
object NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }

    @Single
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .connectTimeout(NetworkConstants.CONNECTION_TIMEOUT.seconds)
            .build()

    @Single
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

    @Single
    fun provideRepositoryService(retrofit: Retrofit): RepositoryApiService =
        retrofit.create(RepositoryApiService::class.java)

}
