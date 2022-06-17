package com.emamagic.signup

import com.emamagic.base.base.BaseViewModel
import com.emamagic.signup.contract.SignupAction
import com.emamagic.signup.contract.SignupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
): BaseViewModel<SignupState, SignupAction>() {
    override fun createInitialState(): SignupState {
        TODO("Not yet implemented")
    }

    override fun handleEvent(event: SignupAction) {
        TODO("Not yet implemented")
    }


}