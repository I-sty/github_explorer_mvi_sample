package com.i_sz_app.githubexplorer.presentation.main

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.bitmapFactoryMaxParallelism
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.util.DebugLogger
import coil3.util.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.KoinApplication
import org.koin.ksp.generated.startKoin

@KoinApplication
class RepoApp : Application(), SingletonImageLoader.Factory {
    override fun onCreate() {
        super.onCreate()
        RepoApp().startKoin {
            androidLogger()
            androidContext(this@RepoApp)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        // https://www.youtube.com/watch?v=qQVCtkg-O7w
        return ImageLoader(this@RepoApp).newBuilder()
            .bitmapFactoryMaxParallelism(2) //https://github.com/coil-kt/coil/issues/1927#issuecomment-1827142015
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder().maxSizePercent(this, 0.1).strongReferencesEnabled(true)
                    .build()
            }
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder().maxSizePercent(0.03).directory(cacheDir).build()
            }
            .logger(DebugLogger(minLevel = Logger.Level.Warn))
            .build()
    }
}
