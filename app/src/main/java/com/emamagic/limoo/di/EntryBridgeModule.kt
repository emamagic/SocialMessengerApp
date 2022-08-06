package com.emamagic.limoo.di

import com.emamagic.core.Bridge
import com.emamagic.domain.repositories.ConversationRepository
import com.emamagic.domain.repositories.FileRepository
import com.emamagic.domain.repositories.UserRepository
import com.emamagic.domain.repositories.WorkspaceRepository
import com.emamagic.limoo.AuthUserComponentManager
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object EntryBridgeModule {

    @Bridge
    @Provides
    fun provideUserRepository(
        authUserComponentManager: AuthUserComponentManager
    ): UserRepository {
        return EntryPoints
            .get(authUserComponentManager, AuthEntryPoint::class.java)
            .getUserRepository()
    }

    @Bridge
    @Provides
    fun provideFileRepository(
        authUserComponentManager: AuthUserComponentManager
    ): FileRepository {
        return EntryPoints
            .get(authUserComponentManager, AuthEntryPoint::class.java)
            .getFileRepository()
    }

    @Bridge
    @Provides
    fun provideWorkspaceRepository(
        authUserComponentManager: AuthUserComponentManager
    ): WorkspaceRepository {
        return EntryPoints
            .get(authUserComponentManager, AuthEntryPoint::class.java)
            .getWorkspaceRepository()
    }

    @Bridge
    @Provides
    fun provideConversationRepository(
        authUserComponentManager: AuthUserComponentManager
    ): ConversationRepository {
        return EntryPoints
            .get(authUserComponentManager, AuthEntryPoint::class.java)
            .getConversationRepository()
    }
}