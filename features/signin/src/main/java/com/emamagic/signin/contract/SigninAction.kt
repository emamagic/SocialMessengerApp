package com.emamagic.signin.contract

import com.emamagic.application.base.Action

sealed class SigninAction : Action {

    data class SubmitPhoneNumber(val phoneNumber: String, val countryCode: String): SigninAction()
    object InvalidPhoneNumber: SigninAction()
    data class GetServerConfig(val hostName: String): SigninAction()
    object ServerConfigLoaded: SigninAction()
    object NeedToSignup: SigninAction()

}