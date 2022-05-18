package com.emamagic.signin.contract.redux.middleware

import com.emamagic.mvi.BaseMiddleware
import com.emamagic.mvi.BaseEffect
import com.emamagic.base.interactor.SinginUseCase
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.otp.OtpFragmentDirections
import javax.inject.Inject

class OtpMiddleware @Inject constructor(
    private val signinUseCase: SinginUseCase
) : BaseMiddleware<SigninState, SigninAction>() {

    override suspend fun process(
        action: SigninAction,
        currentState: SigninState,
        store: com.emamagic.mvi.Store<SigninState, SigninAction>
    ) {
        super.process(action, currentState, store)

        when (action) {
            is SigninAction.SubmitOtpVerification -> otpRegistration(action.code, action.phoneNumber, action.deviceId)
        }
    }


    private suspend fun otpRegistration(code: String, phoneNumber: String, deviceId: String) {
        val isSuccessFull = signinUseCase.otpVerification(code, phoneNumber, deviceId).manageResult()
        if (isSuccessFull) {
            store.setEffect(BaseEffect.NavigateTo(OtpFragmentDirections.actionOtpFragmentToConversationFragment()))
        }
    }


}