package com.emamagic.splash

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.ArgsRouter
import com.emamagic.navigation.router.Router
import java.io.Serializable
// ArgsRouter<SplashRouter.Args>
interface SplashRouter: Router {

    sealed class Routes: Route {
        object ToLoginViaPhoneNumber: Routes()
        object ToConversations: Routes()
    }

//    data class Args(val uuid: Long) : Serializable

}