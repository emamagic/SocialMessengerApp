package com.emamagic.chat

import com.emamagic.common_ui.base.BaseViewModel

class ChatViewModel: BaseViewModel<ChatContract.ChatState, ChatContract.ChatEvent, ChatContract.ChatRouter.Routes>() {

    override fun createInitialState(): ChatContract.ChatState = ChatContract.ChatState.initialize()

    override fun handleEvent(event: ChatContract.ChatEvent) {

    }
}