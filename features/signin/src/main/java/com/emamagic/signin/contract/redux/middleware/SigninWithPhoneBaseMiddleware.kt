package com.emamagic.signin.contract.redux.middleware

import android.util.Log
import com.emamagic.mvi.BaseMiddleware
import com.emamagic.mvi.BaseEffect
import com.emamagic.base.interactor.SinginUseCase
import com.emamagic.core.succeeded
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
        store: com.emamagic.mvi.Store<SigninState, SigninAction>
    ) {
        super.process(action, currentState, store)
        when (action) {
            is SigninAction.GetServerConfig -> getServerConfig()
            is SigninAction.SubmitPhoneNumberRegistration -> submitPhoneNumber(action.phoneNumber)
        }
    }


    private suspend fun getServerConfig() {
        val result = singinUseCase.getServerConfig()
        if (result.succeeded) {

        }
    }

    private suspend fun submitPhoneNumber(phoneNumber: PhoneNumber) {
        store.setEffect(BaseEffect.ShowLoading())
        singinUseCase.phoneNumberRegistration(phoneNumber).manageResult {
            if (it) {
                store.setEffect(
                    BaseEffect.NavigateTo(
                    SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToOtpFragment()
                ))
            }
            store.setEffect(BaseEffect.HideLoading)
        }
    }

}