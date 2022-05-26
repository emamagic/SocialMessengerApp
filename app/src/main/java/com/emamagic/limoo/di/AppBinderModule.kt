package com.emamagic.limoo.di

import com.emamagic.androidcore.appinitializer.AppInitializer
import com.emamagic.limoo.appinitializers.TypeFaceInitializer
import com.emamagic.repository.ConversationRepository
import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.repository.ConversationRepositoryImpl
import com.emamagic.repository_impl.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBinderModule {

    @Binds
    abstract fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindConversationRepository(conversationRepositoryImpl: ConversationRepositoryImpl): ConversationRepository

    @Binds
    @IntoSet
    abstract fun bindTypeFaceInitializer(typeFaceInitializer: TypeFaceInitializer): AppInitializer


}