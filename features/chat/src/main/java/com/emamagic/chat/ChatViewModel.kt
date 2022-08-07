package com.emamagic.chat

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.domain.interactors.GetMessages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val getMessages: GetMessages
): BaseViewModel<ChatContract.ChatState, ChatContract.ChatEvent, ChatContract.ChatRouter.Routes>() {

    override fun createInitialState(): ChatContract.ChatState = ChatContract.ChatState.initialize()

    override fun handleEvent(event: ChatContract.ChatEvent) {
        when (event) {
            is ChatContract.ChatEvent.LoadMessage -> loadMessages(event.workspaceId, event.conversationId)
        }
    }

    private fun loadMessages(workspaceId: String, conversationId: String) = viewModelScope.launch {
        getMessages(GetMessages.Params(workspaceId, conversationId, System.currentTimeMillis())).collectLatest {
            setState { copy(messages = it) }
        }
    }

}