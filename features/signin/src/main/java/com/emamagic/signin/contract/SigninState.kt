package com.emamagic.signin.contract

import com.emamagic.application.base.State
import com.emamagic.signin.R

class SigninState(
) : State {
    companion object {
        fun initialize(): SigninState =
            SigninState(
            )
    }
}