package com.emamagic.chat

import com.emamagic.mvi.EVENT
import com.emamagic.mvi.State
import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface ChatContract {

    sealed class ChatEvent: EVENT {

        object GetMyWorkspaces: ChatEvent()

    }

    data class ChatState(
        val conversations: List<String>
    ) : State {
        companion object {
            fun initialize() =
                ChatState(
                    conversations = emptyList()
                )
        }
    }

    interface ChatRouter: Router {
        interface Routes: Route
    }

}