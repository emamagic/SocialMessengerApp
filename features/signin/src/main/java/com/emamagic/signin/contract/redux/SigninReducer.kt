package com.emamagic.signin.contract.redux

import com.emamagic.mvi.Reducer
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState

class SigninReducer : com.emamagic.mvi.Reducer<SigninState, SigninAction> {

    override fun reduce(currentState: SigninState, action: SigninAction): SigninState {
        return when (action) {
            is SigninAction.ServerConfigLoaded -> currentState.copy(serverConfigLoaded = true)
            else -> currentState
        }
    }
}