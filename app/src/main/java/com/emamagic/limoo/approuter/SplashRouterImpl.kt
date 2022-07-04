package com.emamagic.limoo.approuter

import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.emamagic.limoo.R
import com.emamagic.navigation.exception.IllegalRouteException
import com.emamagic.navigation.route.DeepLink
import com.emamagic.navigation.route.Route
import com.emamagic.splash.SplashRouter

// todo if we have just AppRouter we can injected here (not in separated module)
class SplashRouterImpl: BaseRouter(), SplashRouter {

    // todo it can take into BaseRouter
    override fun push(instance: Fragment, route: Route) {
        if (route is SplashRouter.Routes) { // todo or you can use hashMap for multibinding and get right Router in BaseFragment
            when (route) {
                SplashRouter.Routes.ToLogin -> DeepLink.ACTION_TO_LOGIN.toUri()
                SplashRouter.Routes.ToConversations -> DeepLink.ACTION_TO_CONVERSATIONS.toUri()
                else -> null
            }?.let {
                try {
                    instance.findNavController().navigate(it, NavOptions.Builder().setPopUpTo(com.emamagic.navigation.R.id.splash_graph, true).build())
                } catch (t: Throwable) {
                    Log.e(TAG, "push: ${t.stackTraceToString()}")
                }
            } ?: throw IllegalRouteException(instance, route)
        }
    }


}