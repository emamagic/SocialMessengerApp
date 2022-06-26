package com.emamagic.limoo.approuter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.*
import com.emamagic.conversation.ConversationRouter
import com.emamagic.login.LoginRouter
import com.emamagic.navigation.LoginGraphDirections
import com.emamagic.navigation.exception.IllegalRouteException
import com.emamagic.navigation.route.Route
import com.emamagic.signup.ProfileRouter
import com.emamagic.splash.SplashRouter

// todo if we have just AppRouter we can injected here (not in separated module)
class SplashRouter: BaseRouter(), SplashRouter {

    // todo it can take into BaseRouter
    override fun push(instance: Fragment, route: SplashRouter.Routes) {
        when (route) {
            SplashRouter.Routes.Login -> LoginGraphDirections.actionToLogin()
            else -> null
        }?.let {
            Log.e("TAG", "push: ")
            try {
                val t =
                    Navigation.findNavController(instance.requireActivity(), com.emamagic.navigation.R.id.nav_graph).currentDestination
                Log.e("TAG", "push: $t", )
            } catch (t: Throwable) {
                Log.e("TAG", "push: ${t.stackTraceToString()}", )

            }
        } ?: throw IllegalRouteException(instance, route)
    }


}