package com.emamagic.limoo.di

import com.emamagic.core.AuthUserScope
import com.emamagic.data.repositories.*
import com.emamagic.domain.repositories.*
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

    @AuthUserScope
    @Binds
    abstract fun bindMessageRepository(messageRepositoryImpl: MessageRepositoryImpl): MessageRepository

}