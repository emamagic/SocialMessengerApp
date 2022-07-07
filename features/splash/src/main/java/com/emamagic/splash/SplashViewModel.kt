package com.emamagic.splash

import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.LimooHttpCode
import com.emamagic.domain.interactors.*
import com.emamagic.mvi.BaseEffect
import com.emamagic.splash.contract.SplashEvent
import com.emamagic.splash.contract.SplashRouter
import com.emamagic.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val checkLoginProcess: CheckLoginProcess
) : BaseViewModel<SplashState, SplashEvent, SplashRouter.Routes>() {

    override fun createInitialState(): SplashState = SplashState.initialize()

    override fun handleEvent(event: SplashEvent) {}

    init {
        withLoadingScope {
            getCurrentUser(Unit).manageResult(
                success = {
                    when (checkLoginProcess()) {
                        CheckLoginProcess.LoginProcessResult.GoToConversation -> routerDelegate.pushRoute(SplashRouter.Routes.ToConversations)
                        CheckLoginProcess.LoginProcessResult.GoToIntro -> routerDelegate.pushRoute(SplashRouter.Routes.ToSignup)
                        CheckLoginProcess.LoginProcessResult.GoToWorkspaceCreate -> routerDelegate.pushRoute(SplashRouter.Routes.ToWorkspaceCreate)
                        CheckLoginProcess.LoginProcessResult.GoToWorkspaceSelect -> routerDelegate.pushRoute(SplashRouter.Routes.ToWorkspaceSelect)
                    }
                },
                failed = { // HTTP_UNAUTHORIZED status handled in BaseViewModel
                if (it.statusCode == LimooHttpCode.HTTP_SIGNUP) { // user need to signup
                    routerDelegate.pushRoute(SplashRouter.Routes.ToSignup)
                }
            })
        }
    }

}