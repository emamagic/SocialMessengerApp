package com.emamagic.signup.contract.redux

import com.emamagic.mvi.BaseMiddleware
import com.emamagic.mvi.Store
import com.emamagic.signup.contract.SignupAction
import com.emamagic.signup.contract.SignupState
import javax.inject.Inject

class SignupMiddleware @Inject constructor(

): BaseMiddleware<SignupState, SignupAction>() {

    override suspend fun process(
        action: SignupAction,
        currentState: SignupState,
        store: Store<SignupState, SignupAction>
    ) {
        super.process(action, currentState, store)


    }
}