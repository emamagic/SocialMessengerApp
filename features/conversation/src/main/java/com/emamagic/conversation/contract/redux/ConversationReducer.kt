package com.emamagic.conversation.contract.redux

import com.emamagic.mvi.Reducer
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState

class ConversationReducer: com.emamagic.mvi.Reducer<ConversationState, ConversationAction> {

    override fun reduce(
        currentState: ConversationState,
        action: ConversationAction
    ): ConversationState {
        return when (action) {
            else -> currentState
        }
    }
}