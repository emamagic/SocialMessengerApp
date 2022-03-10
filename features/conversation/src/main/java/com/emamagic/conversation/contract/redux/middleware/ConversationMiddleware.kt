package com.emamagic.conversation.contract.redux.middleware

import com.emamagic.application.base.BaseMiddleware
import com.emamagic.application.base.Store
import com.emamagic.application.interactor.CommonUserCase
import com.emamagic.application.interactor.ConversationUseCase
import com.emamagic.application.interactor.SinginUseCase
import com.emamagic.common_jvm.NoCurrentUserFoundException
import com.emamagic.common_jvm.succeeded
import com.emamagic.conversation.contract.ConversationAction
import com.emamagic.conversation.contract.ConversationState
import com.emamagic.entity.User
import javax.inject.Inject

class ConversationMiddleware @Inject constructor(
    private val conversationUseCase: ConversationUseCase,
    private val commonUserCase: CommonUserCase
) : BaseMiddleware<ConversationState, ConversationAction>() {

    override suspend fun process(
        action: ConversationAction,
        currentState: ConversationState,
        store: Store<ConversationState, ConversationAction>
    ) {
        super.process(action, currentState, store)

        when (action) {
            is ConversationAction.GetMyWorkspaces -> getCurrentUser()
        }
    }


    private suspend fun getMyWorkspaces() {

    }

    private suspend fun getCurrentUser() {
        val result = commonUserCase.getCurrentUser()
        if (result.succeeded) ""
        else throw NoCurrentUserFoundException()

    }

}