package com.emamagic.signup.contract

import com.emamagic.mvi.State

data class SignupState(
    val test: String
): State {
    companion object {
        fun initialize() = SignupState(test = "test")
    }
}