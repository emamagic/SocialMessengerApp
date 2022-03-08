package com.emamagic.signin.contract.redux.middleware

import com.emamagic.application.base.BaseMiddleware
import com.emamagic.application.base.Store
import com.emamagic.application.interactor.SinginUseCase
import com.emamagic.entity.PhoneNumber
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
        super.process(action, currentState, store)
        when (action) {
            is SigninAction.GetServerConfig -> getServerConfig(action.hostName)
            is SigninAction.SubmitPhoneNumber -> submitPhoneNumber(action.phoneNumber)
        }

    }


    private suspend fun getServerConfig(hostName: String) {
        singinUseCase.getServerConfig(hostName).manageResult {

        }

    }

    private suspend fun submitPhoneNumber(phoneNumber: PhoneNumber) {
//            store.setEffect(BaseEffect.NavigateTo(SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToOtpFragment()))

            singinUseCase.submitPhoneNumber(phoneNumber)

    }

}