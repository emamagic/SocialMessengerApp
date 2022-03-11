package com.emamagic.limoo.di

import com.emamagic.repository.ConversationRepository
import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.repository.ConversationRepositoryImpl
import com.emamagic.repository_impl.repository.UserRepositoryImpl
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.CookieCache
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
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

    @Binds
    abstract fun bindPersistentCookieJar(persistentCookieJar: PersistentCookieJar): ClearableCookieJar

    @Binds
    abstract fun bindCookieCache(cookieCache: SetCookieCache): CookieCache

}