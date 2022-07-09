package com.emamagic.signup.contract

import android.net.Uri
import com.emamagic.mvi.EVENT

sealed class SignupEvent: EVENT {
    object UserSawIntro: SignupEvent()
    data class UserPickedAvatar(val uri: Uri): SignupEvent()
    data class Signup(val firstName: String?, val lastName: String?, val email: String?): SignupEvent()
}