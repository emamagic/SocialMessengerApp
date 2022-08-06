package com.emamagic.limoo.approuter

import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.emamagic.conversations.ConversationsRouter
import com.emamagic.navigation.exception.IllegalRouteException
import com.emamagic.navigation.route.Route

class ConversationRouterImpl : BaseRouter(), ConversationsRouter {

    override fun push(instance: Fragment, route: Route) {
        if (route is ConversationsRouter.Routes) {
            when (route) {
                is ConversationsRouter.Routes.ToChat -> "https://test.limonadapp.ir/Limonad/workspace/${route.workspaceId}/conversation/${route.conversationId}/message/test".toUri()
                else -> null
            }?.let {
                try {
                    instance.findNavController().navigate(it, NavOptions.Builder().build())
                } catch (t: Throwable) {
                    Log.e(TAG, "push: ${t.stackTraceToString()}")
                }
            } ?: throw IllegalRouteException(instance, route)
        }
    }
}