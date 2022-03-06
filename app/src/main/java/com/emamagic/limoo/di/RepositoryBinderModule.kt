package com.emamagic.limoo.di

import com.emamagic.repository.UserRepository
import com.emamagic.repository_impl.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBinderModule {

    @Binds
    abstract fun bindServerConfigRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

}