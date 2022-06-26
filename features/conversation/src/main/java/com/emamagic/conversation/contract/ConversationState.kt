package com.emamagic.conversation.contract

import com.emamagic.mvi.State

data class ConversationState(
    val t: String
) : State {
    companion object {
        fun initialize() =
            ConversationState(
                t = ""
            )
    }
}