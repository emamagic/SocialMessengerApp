package com.emamagic.navigation.router

import androidx.fragment.app.Fragment
import com.emamagic.navigation.route.DeepLink
import com.emamagic.navigation.route.Route

interface Router {
    fun pop(instance: Fragment)
    fun push(instance: Fragment, route: Route)
}