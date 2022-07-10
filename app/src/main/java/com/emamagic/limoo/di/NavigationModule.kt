package com.emamagic.limoo.di

import com.emamagic.limoo.approuter.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideSplashRouter() = SplashRouterImpl()


    @Provides
    @Singleton
    fun provideLoginRouter() = LoginRouterImpl()

    @Provides
    @Singleton
    fun provideSignupRouter() = SignupRouterImpl()

//    @Provides
//    @Singleton
//    fun provideWorkspaceSelectRouter() = WorkspaceSelectRouterImpl()
//
    @Provides
    @Singleton
    fun provideWorkspaceCreateRouter() = WorkspaceRouterImpl()

}