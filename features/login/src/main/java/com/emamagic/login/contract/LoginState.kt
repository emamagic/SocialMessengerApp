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
        const val INVALID_PHONE_NUMBER = 0
        const val INVALID_USERNAME = 1
        const val INVALID_PASS = 2
        const val INVALID_HOST = 3
        const val INVALID_OTP_CODE = 4
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