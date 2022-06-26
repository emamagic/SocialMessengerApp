package com.emamagic.navigation.exception

import androidx.fragment.app.Fragment
import com.emamagic.navigation.route.Route

class IllegalRouteException(instance: Fragment, Route: Route) : IllegalStateException(
    "unknown route: ${Route.javaClass.simpleName} for instance: ${instance.javaClass.simpleName}"
)