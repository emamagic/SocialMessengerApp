package com.emamagic.signup.contract.redux

import com.emamagic.mvi.Reducer
import com.emamagic.signup.contract.SignupAction
import com.emamagic.signup.contract.SignupState

class SignupReducer: Reducer<SignupState, SignupAction> {
    override fun reduce(currentState: SignupState, action: SignupAction): SignupState {
        TODO("Not yet implemented")
    }
}