package com.emamagic.splash.contract

import com.emamagic.mvi.State

data class SplashState(
    val closeApp: Boolean
) : State {
    companion object {
        fun initialize() = SplashState(
            closeApp = false
        )
    }
}