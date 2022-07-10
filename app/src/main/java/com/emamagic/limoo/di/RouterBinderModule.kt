package com.emamagic.limoo.di

import com.emamagic.limoo.approuter.*
import com.emamagic.login.contract.LoginRouter
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.splash.contract.SplashRouter
import com.emamagic.workspace.contract.WorkspaceRouter
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

    @Binds
    abstract fun bindWorkspaceCreateRouter(workspaceCreateRouterImpl: WorkspaceRouterImpl): WorkspaceRouter

//    @Binds
//    abstract fun bindWorkspaceSelectRouter(workspaceSelectRouterImpl: WorkspaceSelectRouterImpl): WorkspaceSel


}