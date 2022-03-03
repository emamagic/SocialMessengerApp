package com.emamagic.conversation.contract.redux

import com.emamagic.application.base.BaseStore
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import javax.inject.Inject

class ConversationStore @Inject constructor(

): BaseStore<ConversationState, ConversationAction>(
  initialState = ConversationState.initialize(),
  reducer = ConversationReducer(),
    middlewares = emptyList()
)