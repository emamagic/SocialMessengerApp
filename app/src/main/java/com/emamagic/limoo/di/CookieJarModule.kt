package com.emamagic.limoo.di

import android.content.Context
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.CookieCache
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@DisableInstallInCheck
@Module
object CookieJarModule {

    @Provides
    fun provideCookieCache(): CookieCache = SetCookieCache()

    @Provides
    fun provideSharedPrefsCookiePersistor(@ApplicationContext applicationContext: Context): CookiePersistor =
        SharedPrefsCookiePersistor(applicationContext)

    @Provides
    fun providePersistentCookieJar(
        cookieCache: CookieCache,
        cookiePersistor: CookiePersistor
    ): ClearableCookieJar =
        PersistentCookieJar(cookieCache, cookiePersistor)
}