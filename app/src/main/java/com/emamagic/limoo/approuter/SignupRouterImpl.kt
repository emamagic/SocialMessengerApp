package com.emamagic.limoo.approuter

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.emamagic.navigation.exception.IllegalRouteException
import com.emamagic.navigation.route.DeepLink
import com.emamagic.navigation.route.Route
import com.emamagic.signup.SignupFragmentDirections
import com.emamagic.signup.contract.SignupRouter

@Suppress("IMPLICIT_CAST_TO_ANY")
class SignupRouterImpl: BaseRouter(), SignupRouter {

    override fun push(instance: Fragment, route: Route) {
        if (route is SignupRouter.Routes) {
            when (route) {
                SignupRouter.Routes.ToIntro -> SignupFragmentDirections.actionSignupFragmentToIntroFragment()
                SignupRouter.Routes.ToConversation -> DeepLink.ACTION_TO_CONVERSATIONS.toUri()
                SignupRouter.Routes.ToWorkspaceCreate -> DeepLink.ACTION_TO_WORKSPACE_CREATE.toUri()
                SignupRouter.Routes.ToWorkspaceSelect -> DeepLink.ACTION_TO_WORKSPACE_SELECT.toUri()
                else -> null
            }?.let {
                try {
                    when (it) {
                        is Uri -> instance.findNavController().navigate(it, NavOptions.Builder().setPopUpTo(
                            com.emamagic.navigation.R.id.signup_graph, true).build())
                        is NavDirections -> instance.findNavController().navigate(it, NavOptions.Builder().setPopUpTo(
                            com.emamagic.navigation.R.id.signup_fragment, true
                        ).build())
                        else -> null
                    }
                } catch (t: Throwable) {
                    Log.e(TAG, "push: ${t.stackTraceToString()}")
                }
            } ?: throw IllegalRouteException(instance, route)
        }
    }
}