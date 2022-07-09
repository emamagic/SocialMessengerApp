package com.emamagic.limoo.di

import com.emamagic.login.contract.LoginRouter
import com.emamagic.navigation.router.Router
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.splash.contract.SplashRouter
import com.emamagic.workspace_create.contract.WorkspaceCreateRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module(includes = [RouterBinderModule::class])
abstract class RouterBindingModule {

    @IntoSet
    @Binds
    abstract fun bindSplashRouter(splashRouter: SplashRouter): Router

    @IntoSet
    @Binds
    abstract fun bindLoginRouter(loginRouter: LoginRouter): Router

    @IntoSet
    @Binds
    abstract fun bindSignupRouter(signupRouter: SignupRouter): Router

    @IntoSet
    @Binds
    abstract fun bindWorkspaceCreateRouter(workspaceCreateRouter: WorkspaceCreateRouter): Router
}