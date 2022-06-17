package com.emamagic.limoo.di

import com.emamagic.base.appinitializer.AppInitializer
import com.emamagic.limoo.appinitializers.TypeFaceInitializer
import com.emamagic.data_android.interceptor.repositories.ConversationRepositoryImpl
import com.emamagic.data_android.interceptor.repositories.UserRepositoryImpl
import com.emamagic.domain.repositories.ConversationRepository
import com.emamagic.domain.repositories.UserRepository
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