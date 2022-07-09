package com.emamagic.signup.contract

import com.emamagic.mvi.State

data class SignupState(
    val avatarUrl: String?
): State {
    companion object {
        const val FIRST_NAME_INVALID = 0
        const val LAST_NAME_INVALID = 1
        const val EMAIL_INVALID = 2
        fun initialize() = SignupState(avatarUrl = null)
    }
}