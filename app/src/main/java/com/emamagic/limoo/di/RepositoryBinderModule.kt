package com.emamagic.limoo.di

import com.emamagic.repository.ConversationRepository
import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.repository.ConversationRepositoryImpl
import com.emamagic.repository_impl.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBinderModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

}