package com.emamagic.limoo.di

import com.emamagic.limoo.approuter.LoginRouterImpl
import com.emamagic.limoo.approuter.SplashRouterImpl
import com.emamagic.login.LoginRouter
import com.emamagic.splash.SplashRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
abstract class RouterBinderModule {

    @Binds
    abstract fun bindSplashRouter(splashRouterImpl: SplashRouterImpl): SplashRouter

    @Binds
    abstract fun bindLoginRouter(loginRouterImpl: LoginRouterImpl): LoginRouter


}