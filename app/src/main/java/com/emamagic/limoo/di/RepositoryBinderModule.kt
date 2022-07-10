package com.emamagic.limoo.di

import com.emamagic.core.AuthUserScope
import com.emamagic.data.repositories.ConversationRepositoryImpl
import com.emamagic.data.repositories.FileRepositoryImpl
import com.emamagic.data.repositories.UserRepositoryImpl
import com.emamagic.data.repositories.WorkspaceRepositoryImpl
import com.emamagic.domain.repositories.ConversationRepository
import com.emamagic.domain.repositories.FileRepository
import com.emamagic.domain.repositories.UserRepository
import com.emamagic.domain.repositories.WorkspaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn

@Module
@InstallIn(AuthUserComponent::class)
abstract class RepositoryBinderModule {

    @AuthUserScope
    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @AuthUserScope
    @Binds
    abstract fun bindConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

    @AuthUserScope
    @Binds
    abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl): FileRepository

    @AuthUserScope
    @Binds
    abstract fun bindWorkspaceRepository(workspaceRepositoryImpl: WorkspaceRepositoryImpl): WorkspaceRepository

}