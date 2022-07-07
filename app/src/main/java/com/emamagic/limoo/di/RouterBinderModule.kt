package com.emamagic.limoo.di

import com.emamagic.limoo.approuter.LoginRouterImpl
import com.emamagic.limoo.approuter.SignupRouterImpl
import com.emamagic.limoo.approuter.SplashRouterImpl
import com.emamagic.limoo.approuter.WorkspaceSelectRouterImpl
import com.emamagic.login.contract.LoginRouter
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.splash.contract.SplashRouter
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

    @Binds
    abstract fun bindSignupRouter(signupRouterImpl: SignupRouterImpl): SignupRouter
//
//    @Binds
//    abstract fun bindWorkspaceSelectRouter(workspaceSelectRouterImpl: WorkspaceSelectRouterImpl): WorkspaceSel


}