package com.emamagic.conversations.contract

import com.emamagic.conversations.ConversationWrapper
import com.emamagic.mvi.State

data class ConversationsState(
    val conversations: List<ConversationWrapper>
) : State {
    companion object {
        fun initialize() =
            ConversationsState(
                conversations = emptyList()
            )
    }
}