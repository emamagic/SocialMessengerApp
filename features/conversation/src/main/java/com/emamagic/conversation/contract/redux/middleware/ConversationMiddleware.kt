package com.emamagic.conversation.contract.redux.middleware

import com.emamagic.mvi.BaseMiddleware
import com.emamagic.base.interactor.CommonUserCase
import com.emamagic.base.interactor.ConversationUseCase
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import javax.inject.Inject

class ConversationMiddleware @Inject constructor(
    private val conversationUseCase: ConversationUseCase,
    private val commonUserCase: CommonUserCase
) : BaseMiddleware<ConversationState, ConversationAction>() {

    override suspend fun process(
        action: ConversationAction,
        currentState: ConversationState,
        store: com.emamagic.mvi.Store<ConversationState, ConversationAction>
    ) {
        super.process(action, currentState, store)

        when (action) {
            is ConversationAction.GetMyWorkspaces -> getCurrentUser()
        }
    }


    private suspend fun getMyWorkspaces() {

    }

    private suspend fun getCurrentUser() {
        commonUserCase.getCurrentUser().manageResult()

//        throw NoCurrentUserFoundException()

    }

}