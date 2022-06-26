package com.emamagic.splash.contract

import com.emamagic.mvi.State

data class SplashState(
    val test: String
) : State {
    companion object {
        fun initialize() = SplashState(
            test = "test"
        )
    }
}