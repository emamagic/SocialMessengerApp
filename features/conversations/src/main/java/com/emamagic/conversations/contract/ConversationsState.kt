package com.emamagic.conversations.contract

import com.emamagic.mvi.State

data class ConversationsState(
    val t: String
) : State {
    companion object {
        fun initialize() =
            ConversationsState(
                t = ""
            )
    }
}