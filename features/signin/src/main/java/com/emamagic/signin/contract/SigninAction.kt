package com.emamagic.signin.contract

import com.emamagic.application.base.Action
import com.emamagic.entity.PhoneNumber

sealed class SigninAction : Action {

    data class SubmitPhoneNumber(val phoneNumber: PhoneNumber): SigninAction()
    data class GetServerConfig(val hostName: String): SigninAction()
    object ServerConfigLoaded: SigninAction()
    object NeedToSignup: SigninAction()

}