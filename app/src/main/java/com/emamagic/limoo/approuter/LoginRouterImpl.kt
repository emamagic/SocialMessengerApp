package com.emamagic.limoo.approuter

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.emamagic.login.LoginRouter
import com.emamagic.login.phone.LoginViaPhoneNumberFragmentDirections
import com.emamagic.navigation.exception.IllegalRouteException
import com.emamagic.navigation.route.DeepLink
import com.emamagic.navigation.route.Route

@Suppress("IMPLICIT_CAST_TO_ANY")
class LoginRouterImpl: BaseRouter(), LoginRouter {

    override fun push(instance: Fragment, route: Route) {
        if (route is LoginRouter.Routes) {
            when (route) {
                LoginRouter.Routes.LoginViaPhoneNumberToChangeServer -> LoginViaPhoneNumberFragmentDirections.actionToChangeServerNameFragment()
                LoginRouter.Routes.OtpToConversations -> DeepLink.ACTION_TO_CONVERSATIONS.toUri()
                LoginRouter.Routes.LoginViaPhoneNumberToLoginViaUsername -> LoginViaPhoneNumberFragmentDirections.actionToLoginViaUsernameFragment()
                LoginRouter.Routes.LoginViaPhoneNumberToOtp -> LoginViaPhoneNumberFragmentDirections.actionToOtpFragment()
                LoginRouter.Routes.UsernameToConversations -> DeepLink.ACTION_TO_CONVERSATIONS.toUri()
                else -> null
            }?.let {
                try {
                    when (it) {
                        is Uri -> instance.findNavController().navigate(it)
                        is NavDirections -> instance.findNavController().navigate(it)
                        else -> null
                    }
                } catch (t: Throwable) {
                    Log.e(TAG, "push: ${t.stackTraceToString()}")
                }
            } ?: throw IllegalRouteException(instance, route)
        }
    }
}