package com.emamagic.signin.contract.redux.middleware

import com.emamagic.application.base.BaseEffect
import com.emamagic.application.base.BaseMiddleware
import com.emamagic.application.base.Store
import com.emamagic.application.interactor.SinginUseCase
import com.emamagic.common_jvm.onSuccess
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import javax.inject.Inject

class SigninWithPhoneBaseMiddleware @Inject constructor(
    private val singinUseCase: SinginUseCase
) : BaseMiddleware<SigninState, SigninAction>() {

    override suspend fun process(
        action: SigninAction,
        currentState: SigninState,
        store: Store<SigninState, SigninAction>
    ) {

        when (action) {
            is SigninAction.GetServerConfig -> getServerConfig(action.hostName, store)
        }

    }


    private suspend fun getServerConfig(hostName: String, store: Store<SigninState, SigninAction>) {
        singinUseCase.getServerConfig(hostName).onSuccess {
            store.setEffect(BaseEffect.Toast("loaded"))
        }
    }

}