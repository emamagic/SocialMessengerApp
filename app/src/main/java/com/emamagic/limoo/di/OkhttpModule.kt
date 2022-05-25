package com.emamagic.limoo.di

import android.content.Context
import coil.Coil
import coil.ImageLoader
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
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object OkhttpModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        // TODO use it in debug mode
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Singleton
    @Provides
    fun provideOkHttp(
        @ApplicationContext applicationContext: Context,
        loggingInterceptor: HttpLoggingInterceptor,
        connectivityInterceptor: ConnectivityInterceptor,
        hostSelectionInterceptor: HostSelectionInterceptor,
        appAuthenticator: AppAuthenticator,
        persistentCookieJar: ClearableCookieJar,
        threadPoolExecutor: ThreadPoolExecutor
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(connectivityInterceptor)
            .addInterceptor(hostSelectionInterceptor)
            .authenticator(appAuthenticator)
            .cookieJar(persistentCookieJar)
            .dispatcher(Dispatcher(threadPoolExecutor))
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
