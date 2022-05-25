package com.emamagic.limoo.di

import com.emamagic.core.CoDispatcher
import com.emamagic.core.DispatcherType
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ThreadPoolModule {

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

}
