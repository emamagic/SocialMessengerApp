package com.emamagic.login.contract

import com.emamagic.domain.types.AuthType
import com.emamagic.domain.types.DeploymentType
import com.emamagic.mvi.State

data class LoginState(
    @AuthType
    var authType: String,
    @AuthType
    var defaultAuthType: String,
    @DeploymentType
    var deploymentType: String,
    var minPasswordLength: Int,
    val closeApp: Boolean
) : State {
    companion object {
        fun initialize(): LoginState =
            LoginState(
                authType = AuthType.ALL,
                defaultAuthType = AuthType.PHONE_NUMBER,
                deploymentType = DeploymentType.SAAS,
                minPasswordLength = 0,
                closeApp = false
            )
    }
}