package com.emamagic.limoo.approuter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.emamagic.navigation.exception.IllegalRouteException
import com.emamagic.navigation.route.Route
import com.emamagic.workspace_create.contract.WorkspaceCreateRouter

class WorkspaceCreateRouterImpl: BaseRouter(), WorkspaceCreateRouter {

    override fun push(instance: Fragment, route: Route) {
        if (route is WorkspaceCreateRouter.Routes) {
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