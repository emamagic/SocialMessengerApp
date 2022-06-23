package com.emamagic.limoo.di

import android.content.Context
import com.emamagic.cache.cache.preferences.get
import com.emamagic.cache.cache.preferences.pref
import com.emamagic.core.PrefKeys
import com.emamagic.core_android.keycloak.AuthStateManager
import com.emamagic.core_android.keycloak.CertificateManager
import com.emamagic.core_android.keycloak.KeycloakConnectionBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AppAuthConfiguration
import net.openid.appauth.AuthorizationService

@InstallIn(SingletonComponent::class)
@Module
object KeycloakModule {

    @Provides
    fun provideAuthorizationService(@ApplicationContext context: Context): AuthorizationService {
        val alias = pref[PrefKeys.CertAlias, ""]
        val sslContext = CertificateManager.getCertificateSLLContext(context, alias)
        val connectionBuilder = KeycloakConnectionBuilder(sslContext.socketFactory)
        val appAuthConfiguration = AppAuthConfiguration.Builder().setConnectionBuilder(connectionBuilder).build()
        return AuthorizationService(context, appAuthConfiguration)
    }

    @Provides
    fun provideAuthManager(@ApplicationContext context: Context): AuthStateManager = AuthStateManager.getInstance(context)

}