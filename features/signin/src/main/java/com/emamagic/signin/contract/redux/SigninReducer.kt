package com.emamagic.signin.contract.redux

import com.emamagic.application.base.Reducer
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState

class SigninReducer: Reducer<SigninState, SigninAction> {

    override fun reduce(currentState: SigninState, action: SigninAction): SigninState {
        TODO("Not yet implemented")
    }
}