package com.emamagic.splash

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface SplashRouter: Router {

    sealed class Routes: Route {
        object ToLogin: Routes()
        object ToConversations: Routes()
    }

}