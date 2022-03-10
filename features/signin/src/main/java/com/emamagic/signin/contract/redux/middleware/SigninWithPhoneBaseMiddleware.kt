package com.emamagic.signin.contract.redux.middleware

import com.emamagic.application.base.BaseEffect
import com.emamagic.application.base.BaseMiddleware
import com.emamagic.application.base.Store
import com.emamagic.application.interactor.SinginUseCase
import com.emamagic.common_jvm.succeeded
import com.emamagic.entity.PhoneNumber
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.phone.SigninWithPhoneFragmentDirections
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
            is SigninAction.SubmitPhoneNumberRegistration -> submitPhoneNumber(action.phoneNumber)
        }
    }


    private suspend fun getServerConfig(hostName: String) {
        val result = singinUseCase.getServerConfig(hostName)
        if (result.succeeded) {

        }
    }

    private suspend fun submitPhoneNumber(phoneNumber: PhoneNumber) {
        store.setEffect(BaseEffect.ShowLoading())
        singinUseCase.phoneNumberRegistration(phoneNumber).manageResult {
            if (it) {
                store.setEffect(BaseEffect.NavigateTo(
                    SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToOtpFragment()
                ))
            }
            store.setEffect(BaseEffect.HideLoading)
        }
    }

}