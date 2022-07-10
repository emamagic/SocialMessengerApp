package com.emamagic.limoo.approuter

import androidx.fragment.app.Fragment
import com.emamagic.navigation.route.Route
import com.emamagic.workspace.contract.WorkspaceRouter

class WorkspaceRouterImpl: BaseRouter(), WorkspaceRouter {

    override fun push(instance: Fragment, route: Route) {
        if (route is WorkspaceRouter.Routes) {
//            when (route) {
//
//                else -> null
//            }?.let {
//                try {
//                    instance.findNavController().navigate(it, NavOptions.Builder().setPopUpTo(com.emamagic.navigation.R.id.splash_graph, true).build())
//                } catch (t: Throwable) {
//                    Log.e(TAG, "push: ${t.stackTraceToString()}")
//                }
//            } ?: throw IllegalRouteException(instance, route)
        }
    }
}