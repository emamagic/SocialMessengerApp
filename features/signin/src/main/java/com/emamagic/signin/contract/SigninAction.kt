package com.emamagic.signin.contract

import com.emamagic.application.base.Action

sealed class SigninAction : Action {

    data class SubmitPhoneNumber(val phoneNumber: String, val countryCode: String): SigninAction()
    object InvalidPhoneNumber: SigninAction()
    object ConfigUpdate: SigninAction()
    object NeedToSignup: SigninAction()

}