package com.emamagic.limoo.di

import com.emamagic.core.AuthUserScope
import com.emamagic.data_android.interceptor.network.RestProvider
import com.emamagic.data_android.interceptor.repositories.ConversationRepositoryImpl
import com.emamagic.data_android.interceptor.repositories.UserRepositoryImpl
import com.emamagic.domain.repositories.ConversationRepository
import com.emamagic.domain.repositories.UserRepository
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



}