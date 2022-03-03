package com.emamagic.signin.contract

import com.emamagic.application.base.State
import com.emamagic.signin.R

data class SigninState(
    val serverConfigLoaded: Boolean
) : State {
    companion object {
        fun initialize(): SigninState =
            SigninState(
                serverConfigLoaded = false
            )
    }
}