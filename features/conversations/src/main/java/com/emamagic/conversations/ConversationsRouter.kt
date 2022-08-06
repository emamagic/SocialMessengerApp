package com.emamagic.conversations

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface ConversationsRouter: Router {
    sealed class Routes : Route {
        data class ToChat(val workspaceId: String, val conversationId: String): Routes()
    }
}