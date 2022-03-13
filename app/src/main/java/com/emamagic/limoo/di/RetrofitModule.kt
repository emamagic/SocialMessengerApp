package com.emamagic.limoo.di

import android.content.Context
import com.emamagic.network.interceptor.AppAuthenticator
import com.emamagic.network.interceptor.ConnectivityInterceptor
import com.emamagic.network.interceptor.HostSelectionInterceptor
import com.emamagic.network.service.ConfigService
import com.emamagic.network.service.ConversationService
import com.emamagic.network.service.UserService
import com.emamagic.network.util.Const
import com.emamagic.network.util.ResponseConverter
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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RetrofitModule {

    @Provides
    fun provideCookiePersistor(@ApplicationContext context: Context): CookiePersistor {
        return SharedPrefsCookiePersistor(context)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor,
        connectivityInterceptor: ConnectivityInterceptor,
        hostSelectionInterceptor: HostSelectionInterceptor,
        persistentCookieJar: PersistentCookieJar,
        appAuthenticator: AppAuthenticator
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(hostSelectionInterceptor)
            .authenticator(appAuthenticator)
            .addInterceptor(connectivityInterceptor)
            .cookieJar(persistentCookieJar)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(8, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: Lazy<OkHttpClient>): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                ResponseConverter(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .baseUrl(Const.BASE_URL)
            .callFactory { request ->
                // this bellow fun ,called in background thread
                client.get().newCall(request)
            }
            .build()
    }

    @Provides
    fun provideConfigService(retrofit: Retrofit): ConfigService =
        retrofit.create(ConfigService::class.java)

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    fun provideConversationService(retrofit: Retrofit): ConversationService =
        retrofit.create(ConversationService::class.java)

}
