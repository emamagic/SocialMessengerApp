package com.emamagic.limoo.approuter

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.emamagic.login.contract.LoginRouter
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
                LoginRouter.Routes.ToConversations -> DeepLink.ACTION_TO_CONVERSATIONS.toUri()
                LoginRouter.Routes.LoginViaPhoneNumberToLoginViaUsername -> LoginViaPhoneNumberFragmentDirections.actionToLoginViaUsernameFragment()
                LoginRouter.Routes.LoginViaPhoneNumberToOtp -> LoginViaPhoneNumberFragmentDirections.actionToOtpFragment()
                LoginRouter.Routes.ToSignup -> DeepLink.ACTION_TO_SIGNUP.toUri()
                LoginRouter.Routes.ToWorkspaceSelect -> DeepLink.ACTION_TO_WORKSPACE_SELECT.toUri()
                LoginRouter.Routes.ToWorkspaceCreate -> DeepLink.ACTION_TO_WORKSPACE_CREATE.toUri()
                else -> null
            }?.let {
                try {
                    when (it) {
                        is Uri -> instance.findNavController().navigate(it, NavOptions.Builder().setPopUpTo(
                            com.emamagic.navigation.R.id.login_graph, true).build())
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