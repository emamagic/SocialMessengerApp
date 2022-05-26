package com.emamagic.limoo.di

import android.content.Context
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.emamagic.limoo.BuildConfig
import com.emamagic.network.interceptor.AppAuthenticator
import com.emamagic.network.interceptor.ConnectivityInterceptor
import com.emamagic.network.interceptor.DownloadProgressInterceptor
import com.emamagic.network.interceptor.HostSelectionInterceptor
import com.emamagic.network.service.ConfigService
import com.emamagic.network.service.UserService
import com.emamagic.network.util.Const
import com.emamagic.network.util.DownloadProgressResponseBody
import com.emamagic.network.util.ResponseConverter
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import java.io.File
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(includes = [ThreadPoolModule::class, CookieJarModule::class])
object OkhttpModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor? {
        if (!BuildConfig.DEBUG) return null
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideHttpEventListener(): LoggingEventListener.Factory? {
        if (!BuildConfig.DEBUG) return null
        return LoggingEventListener.Factory()
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        @ApplicationContext applicationContext: Context,
        loggingInterceptor: HttpLoggingInterceptor?,
        connectivityInterceptor: ConnectivityInterceptor,
        hostSelectionInterceptor: HostSelectionInterceptor,
        loggingEventListener: LoggingEventListener.Factory?,
        appAuthenticator: AppAuthenticator,
        persistentCookieJar: ClearableCookieJar,
        threadPoolExecutor: ThreadPoolExecutor
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .apply {
                if (loggingInterceptor != null)
                    addInterceptor(loggingInterceptor)
                if (loggingEventListener != null)
                    eventListenerFactory(loggingEventListener)
            }
            .cache(Cache(File(applicationContext.cacheDir, "api_cache"), 50L * 1024 * 1024))
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(hostSelectionInterceptor)
            .authenticator(appAuthenticator)
            .cookieJar(persistentCookieJar)
            .connectionPool(ConnectionPool(10, 2, TimeUnit.MINUTES))
            .dispatcher(Dispatcher(threadPoolExecutor).apply {
                // Allow for increased number of concurrent image fetches on same host.
                maxRequestsPerHost = 10
            })
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(8, TimeUnit.SECONDS)
            .build()
        Coil.setImageLoader {
            ImageLoader.Builder(applicationContext).okHttpClient(client).build()
        }
        return client
    }


}
