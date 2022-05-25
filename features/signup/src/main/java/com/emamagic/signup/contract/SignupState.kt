package com.emamagic.signup.contract

import com.emamagic.mvi.State

data class SignupState(
    val id: String
) : State {
    companion object {
        fun initialize() = SignupState(id = "")
    }
}