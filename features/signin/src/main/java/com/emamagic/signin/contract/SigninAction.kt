package com.emamagic.signin.contract

import com.emamagic.mvi.Action
import com.emamagic.entity.PhoneNumber

sealed class SigninAction : Action {

    data class SubmitPhoneNumberRegistration(val phoneNumber: PhoneNumber): SigninAction()

    object GetServerConfig: SigninAction()
    object ServerConfigLoaded: SigninAction()

    data class SubmitOtpVerification(val code: String, val phoneNumber: String, val deviceId: String): SigninAction()

}