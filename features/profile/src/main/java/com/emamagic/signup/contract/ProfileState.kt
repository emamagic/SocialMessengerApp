package com.emamagic.signup.contract

import com.emamagic.mvi.State

data class ProfileState(
    val id: String
) : State {
    companion object {
        fun initialize() = ProfileState(id = "")
    }
}