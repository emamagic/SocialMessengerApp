package com.emamagic.login.contract

import com.emamagic.mvi.State

data class LoginState(
    var serverConfigUpdated: String?,
    var authTypes: List<String>
) : State {
    companion object {
        fun initialize(): LoginState =
            LoginState(
                serverConfigUpdated = null,
                authTypes = emptyList()
            )
    }
}