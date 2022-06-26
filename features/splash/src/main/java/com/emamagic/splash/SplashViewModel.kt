package com.emamagic.splash

import com.emamagic.base.base.BaseViewModel
import com.emamagic.domain.interactors.UpdateServerConfig
import com.emamagic.splash.contract.SplashEvent
import com.emamagic.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val updateServerConfig: UpdateServerConfig
): BaseViewModel<SplashState, SplashEvent, SplashRouter.Routes>()  {

    init {
        getServerConfig()
    }

    override fun createInitialState(): SplashState = SplashState.initialize()

    override fun handleEvent(event: SplashEvent) {

    }

    private fun getServerConfig() = withLoadingScope {
        updateServerConfig(UpdateServerConfig.Params("https://test.limonadapp.ir", true)).manageResult {
            routerDelegate.pushRoute(SplashRouter.Routes.Login)
        }
    }

}