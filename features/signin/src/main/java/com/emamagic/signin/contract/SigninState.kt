package com.emamagic.signin.contract

import com.emamagic.core.base.State

class SigninState (): State {
    companion object {
        fun initialize(): SigninState =
            SigninState()
    }
}