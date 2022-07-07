package com.emamagic.signup.contract

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface SignupRouter: Router {

    sealed class Routes: Route {
        object ToIntro: Routes()
        object ToConversation: Routes()
        object ToWorkspaceSelect: Routes()
        object ToWorkspaceCreate: Routes()
    }
}