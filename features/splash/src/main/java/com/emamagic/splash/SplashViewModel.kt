package com.emamagic.splash

import android.util.Log
import com.emamagic.base.base.BaseViewModel
import com.emamagic.core.succeeded
import com.emamagic.domain.interactors.GetCurrentUser
import com.emamagic.domain.interactors.GetWorkspaces
import com.emamagic.domain.interactors.UpdateServerConfig
import com.emamagic.mvi.BaseEffect
import com.emamagic.splash.contract.SplashEvent
import com.emamagic.splash.contract.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getCurrentUser: GetCurrentUser,
    private val getWorkspaces: GetWorkspaces
) : BaseViewModel<SplashState, SplashEvent, SplashRouter.Routes>() {

    init {
        withLoadingScope {
                getCurrentUser(Unit).manageResult(success = {
                    // todo check -> get workspaces and check for signup ...
                    getWorkspaces().collect {
                        Log.e("TAG", "my workspaces: ${it.error}", )
                        it.data?.forEach { workspace ->
                            Log.e("TAG", "my workspaces: ${workspace.displayName}", )
                        }
                    }
//                    routerDelegate.pushRoute(SplashRouter.Routes.ToConversations)
                }, failed = {
                    // todo check if this server sets default login to phoneNumber or username or ... and then pass parameter to LoginViaPhoneNumberFragment to process it
                    routerDelegate.pushRoute(SplashRouter.Routes.ToLoginViaPhoneNumber)
                })
        }
    }

    override fun createInitialState(): SplashState = SplashState.initialize()

    override fun handleEvent(event: SplashEvent) {

    }


}