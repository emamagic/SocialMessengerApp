package com.emamagic.signin.contract

import com.emamagic.mvi.State

data class SigninState(
    val serverConfigLoaded: Boolean
) : com.emamagic.mvi.State {
    companion object {
        fun initialize(): SigninState =
            SigninState(
                serverConfigLoaded = false
            )
    }
}