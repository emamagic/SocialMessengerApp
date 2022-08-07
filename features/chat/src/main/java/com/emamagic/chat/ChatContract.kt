package com.emamagic.chat

import androidx.paging.PagingData
import com.emamagic.domain.entities.MessageEntity
import com.emamagic.mvi.EVENT
import com.emamagic.mvi.State
import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface ChatContract {

    sealed class ChatEvent: EVENT {

        data class LoadMessage(val workspaceId: String, val conversationId: String): ChatEvent()

    }

    data class ChatState(
        val messages: PagingData<MessageEntity>?
    ) : State {
        companion object {
            fun initialize() =
                ChatState(
                    messages = null
                )
        }
    }

    interface ChatRouter: Router {
        interface Routes: Route
    }

}