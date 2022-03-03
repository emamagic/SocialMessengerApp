package com.emamagic.network.di

import com.emamagic.network.interceptor.ClientInterceptor
import com.emamagic.network.interceptor.ServerConnection
import com.emamagic.network.service.ConfigService
import com.emamagic.network.util.Const
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(loggingInterceptor: HttpLoggingInterceptor, clientInterceptor: ClientInterceptor, serverInterceptor: ServerConnection): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(serverInterceptor)
            .addInterceptor(clientInterceptor)
            .readTimeout(15 , TimeUnit.SECONDS)
            .writeTimeout(15 , TimeUnit.SECONDS)
            .connectTimeout(8 , TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Const.BASE_URL)
            .callFactory { request ->
                // this bellow fun ,called in background thread
                client.get().newCall(request)
            }
            .build()
    }

    @Provides
    fun provideConfigService(retrofit: Retrofit): ConfigService = retrofit.create(ConfigService::class.java)


}
