package com.emamagic.login.contract

import com.emamagic.mvi.State

data class LoginState(
    val serverConfigLoaded: Boolean
) : State {
    companion object {
        fun initialize(): LoginState =
            LoginState(
                serverConfigLoaded = false
            )
    }
}