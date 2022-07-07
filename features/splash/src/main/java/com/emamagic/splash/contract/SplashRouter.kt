package com.emamagic.splash.contract

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface SplashRouter: Router {

    sealed class Routes: Route {
        object ToConversations: Routes()
        object ToSignup: Routes()
        object ToWorkspaceSelect: Routes()
        object ToWorkspaceCreate: Routes()
    }

}