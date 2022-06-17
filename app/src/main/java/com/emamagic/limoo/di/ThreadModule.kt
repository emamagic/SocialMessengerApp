package com.emamagic.limoo.di

import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.CoDispatcher
import com.emamagic.core.DispatcherType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import kotlinx.coroutines.*
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Singleton

@DisableInstallInCheck
@Module
object ThreadModule {

    @Singleton
    @Provides
    fun provideSharedThreadPoolExecutor(): ThreadPoolExecutor {
        val index = AtomicInteger()
        return ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors().coerceAtLeast(2),
            Int.MAX_VALUE,
            60,
            TimeUnit.SECONDS,
            SynchronousQueue(),
            ThreadFactory { runnable ->
                Thread(runnable, "Shared Thread Pool ${index.incrementAndGet()}")
            }
        )
    }

    @CoDispatcher(DispatcherType.SHARED)
    @Singleton
    @Provides
    fun provideSharedDispatcher(threadPoolExecutor: ThreadPoolExecutor): CoroutineDispatcher =
        threadPoolExecutor.asCoroutineDispatcher()

    @Singleton
    @Provides
    fun provideAppDispatcher() = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )

    @Singleton
    @Provides
    fun provideApplicationScope(dispatchers: AppCoroutineDispatchers): CoroutineScope =
        CoroutineScope(SupervisorJob() + dispatchers.computation)

}
