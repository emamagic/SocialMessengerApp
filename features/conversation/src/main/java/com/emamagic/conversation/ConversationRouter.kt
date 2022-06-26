package com.emamagic.conversation

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface ConversationRouter: Router {
    interface Routes: Route
}