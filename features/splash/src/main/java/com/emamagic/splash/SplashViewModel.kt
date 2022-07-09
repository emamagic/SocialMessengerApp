package com.emamagic.splash

import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.LimooHttpCode
import com.emamagic.domain.interactors.*
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
                        CheckLoginProcess.LoginProcessResult.GoToWorkspaceCreate -> routerDelegate.pushRoute(SplashRouter.Routes.ToWorkspaceCreate)
                        CheckLoginProcess.LoginProcessResult.GoToWorkspaceSelect -> routerDelegate.pushRoute(SplashRouter.Routes.ToWorkspaceSelect)
                    }
                })
        }
    }

}