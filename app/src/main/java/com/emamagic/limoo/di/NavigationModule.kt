package com.emamagic.limoo.di

import com.emamagic.limoo.approuter.LoginRouterImpl
import com.emamagic.limoo.approuter.SplashRouterImpl
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


}