package com.emamagic.conversation.contract.redux

import com.emamagic.application.base.Reducer
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState

class ConversationReducer: Reducer<ConversationState, ConversationAction> {

    override fun reduce(
        currentState: ConversationState,
        action: ConversationAction
    ): ConversationState {
        TODO("Not yet implemented")
    }
}