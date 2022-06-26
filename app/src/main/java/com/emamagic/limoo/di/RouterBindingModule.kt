package com.emamagic.limoo.di

import com.emamagic.navigation.router.Router
import com.emamagic.splash.SplashRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.migration.DisableInstallInCheck
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module(includes = [RouterBinderModule::class])
abstract class RouterBindingModule {

    @IntoSet
    @Binds
    abstract fun bindSplashRouter(splashRouter: SplashRouter): Router
}