package com.emamagic.workspace.contract

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface WorkspaceRouter: Router {

    sealed class Routes : Route {

    }
}