package com.emamagic.conversations

import androidx.lifecycle.viewModelScope
import com.emamagic.base.base.BaseViewModel
import com.emamagic.conversations.contract.ConversationsEvent
import com.emamagic.conversations.contract.ConversationsState
import com.emamagic.core.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationsViewModel @Inject constructor(
): BaseViewModel<ConversationsState, ConversationsEvent, ConversationsRouter.Routes>() {

    init {
        viewModelScope.launch {
//            store.dispatch(ConversationAction.GetMyWorkspaces)
            delay(10000)
            onError(Error(statusCode = 401))
        }
    }

    override fun createInitialState(): ConversationsState = ConversationsState.initialize()

    override fun handleEvent(event: ConversationsEvent) {

    }

}