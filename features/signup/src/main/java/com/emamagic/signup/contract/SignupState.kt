package com.emamagic.signup.contract

import com.emamagic.mvi.State

data class SignupState(
    val avatarHash: String?
): State {
    companion object {
        fun initialize() = SignupState(avatarHash = null)
    }
}