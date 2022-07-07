package com.emamagic.signup.contract

import com.emamagic.mvi.EVENT

sealed class SignupEvent: EVENT {
    object UserSawIntro: SignupEvent()
}