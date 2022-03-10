package com.emamagic.signin.contract.redux.middleware

import com.emamagic.application.base.BaseEffect
import com.emamagic.application.base.BaseMiddleware
import com.emamagic.application.base.Store
import com.emamagic.application.interactor.SinginUseCase
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
        store: Store<SigninState, SigninAction>
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