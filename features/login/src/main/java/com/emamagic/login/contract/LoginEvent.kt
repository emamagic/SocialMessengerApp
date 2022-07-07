package com.emamagic.login.contract

import com.emamagic.mvi.EVENT

sealed class LoginEvent : EVENT {

    data class TypingPhoneNumberEvent(val phoneNumber: String): LoginEvent()
    object ChangeServerNameClickEvent: LoginEvent()
    object LoginWithUsernameClickEvent: LoginEvent()
    object LoginWithPhoneNumberClickEvent: LoginEvent()
    data class SubmitPhoneNumberEvent(val phoneNumber: String, val countryCode: String ): LoginEvent()
    object SubmitTermsPolicyEvent: LoginEvent()
    data class SubmitUserNameEvent(val username: String, val pass: String): LoginEvent()
    data class TypingUserNameOrPassEvent(val username: String, val pass: String): LoginEvent()
    data class ChangeServerNameEvent(val host: String): LoginEvent()
    object LoginWithKeycloakEvent: LoginEvent()
    data class SubmitOtpEvent(val code: String, val deviceId: String): LoginEvent()
    object OtpExpiredEvent: LoginEvent()
    object InitEvent: LoginEvent()


}