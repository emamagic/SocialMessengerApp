package com.emamagic.splash

import com.emamagic.base.base.BaseViewModel
import com.emamagic.domain.interactors.GetCurrentUser
import com.emamagic.domain.interactors.UpdateServerConfig
import com.emamagic.splash.contract.SplashEvent
import com.emamagic.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val updateServerConfig: UpdateServerConfig,
    private val getCurrentUser: GetCurrentUser,
) : BaseViewModel<SplashState, SplashEvent, SplashRouter.Routes>() {

    init {
        withLoadingScope {
            updateServerConfig(UpdateServerConfig.Params("https://test.limonadapp.ir", true)).manageResult { // AnyWay (success or failed) ->
                getCurrentUser(Unit).manageResult(success = {
                    // todo check -> get workspaces and check for signup ...

                    routerDelegate.pushRoute(SplashRouter.Routes.ToConversations)
                }, failed = {
                    // todo check if this server sets default login to phoneNumber or username or ... and then pass parameter to LoginViaPhoneNumberFragment to process it
                    routerDelegate.pushRoute(SplashRouter.Routes.ToLoginViaPhoneNumber)
                })
            }
        }
    }

    override fun createInitialState(): SplashState = SplashState.initialize()

    override fun handleEvent(event: SplashEvent) {

    }


}