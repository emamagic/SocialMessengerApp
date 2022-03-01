package com.emamagic.signin.contract.redux

import com.emamagic.application.base.Middleware
import com.emamagic.application.base.Store
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import javax.inject.Inject

class SigninMiddleware @Inject constructor() : Middleware<SigninState, SigninAction>() {

    override suspend fun process(
        action: SigninAction,
        currentState: SigninState,
        store: Store<SigninState, SigninAction>
    ) {
        TODO("Not yet implemented")
    }
}