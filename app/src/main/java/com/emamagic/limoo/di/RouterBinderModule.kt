package com.emamagic.limoo.di

import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck

@DisableInstallInCheck
@Module
abstract class RouterBinderModule {

    @Binds
    abstract fun bindSplashRouter(splashRouter: com.emamagic.limoo.approuter.SplashRouter): com.emamagic.splash.SplashRouter

//    @Binds
//    abstract fun bindLoginRouter(appRouter: AppRoute): LoginRoute
//
//    @Binds
//    abstract fun bindConversationRouter(appRouter: AppRoute): ConversationRoute
//
//    @Binds
//    abstract fun bindProfileRouter(appRouter: AppRoute): ProfileRoute

}