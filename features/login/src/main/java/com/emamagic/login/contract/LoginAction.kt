package com.emamagic.login.contract

import com.emamagic.domain.interactors.LoginWithPhoneNumber
import com.emamagic.mvi.EVENT

sealed class LoginAction : EVENT {

    data class SubmitPhoneNumberRegistration(val phoneNumber: LoginWithPhoneNumber.Params): LoginAction()

    object GetServerConfig: LoginAction()
    object ServerConfigLoaded: LoginAction()

    data class SubmitOtpVerification(val code: String, val phoneNumber: String, val deviceId: String): LoginAction()

}