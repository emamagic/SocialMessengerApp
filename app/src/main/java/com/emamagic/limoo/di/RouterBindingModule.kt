package com.emamagic.limoo.di

import com.emamagic.conversations.ConversationsRouter
import com.emamagic.login.contract.LoginRouter
import com.emamagic.navigation.router.Router
import com.emamagic.signup.contract.SignupRouter
import com.emamagic.splash.contract.SplashRouter
import com.emamagic.workspace.contract.WorkspaceRouter
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
    abstract fun bindWorkspaceCreateRouter(workspaceRouter: WorkspaceRouter): Router

    @IntoSet
    @Binds
    abstract fun bindConversationRouter(conversationRouter: ConversationsRouter): Router
}