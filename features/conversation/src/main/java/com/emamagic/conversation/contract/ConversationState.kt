package com.emamagic.conversation.contract

import com.emamagic.mvi.State

data class ConversationState(
    val t: String
) : com.emamagic.mvi.State {
    companion object {
        fun initialize() =
            ConversationState(
                t = ""
            )
    }
}