package com.emamagic.signin.contract

import com.emamagic.mvi.EVENT
import com.emamagic.domain.entities.PhoneNumber

sealed class SigninAction : EVENT {

    data class SubmitPhoneNumberRegistration(val phoneNumber: PhoneNumber): SigninAction()

    object GetServerConfig: SigninAction()
    object ServerConfigLoaded: SigninAction()

    data class SubmitOtpVerification(val code: String, val phoneNumber: String, val deviceId: String): SigninAction()

}