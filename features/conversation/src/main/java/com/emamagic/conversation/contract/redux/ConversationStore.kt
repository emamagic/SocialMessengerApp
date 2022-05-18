package com.emamagic.conversation.contract.redux

import com.emamagic.mvi.BaseStore
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import com.emamagic.conversation.contract.redux.middleware.ConversationMiddleware
import javax.inject.Inject

class ConversationStore @Inject constructor(
  conversationMiddleware: ConversationMiddleware
): BaseStore<ConversationState, ConversationAction>(
  initialState = ConversationState.initialize(),
  reducer = ConversationReducer(),
    middlewares = listOf(
      conversationMiddleware
    )
)