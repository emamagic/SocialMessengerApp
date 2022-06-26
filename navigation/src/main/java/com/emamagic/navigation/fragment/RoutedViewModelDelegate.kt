package com.emamagic.navigation.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emamagic.navigation.lifecycle.LiveDataEvent
import com.emamagic.navigation.route.Route

class RoutedViewModelDelegate<ROUTE : Route> : RoutedViewModel {

    private val _route = MutableLiveData<LiveDataEvent<Route>>()
    override val iRoute: LiveData<LiveDataEvent<Route>>
        get() = _route

    fun pushRoute(route: ROUTE) {
        _route.value = LiveDataEvent(route)
    }

    fun popRoute() {
        _route.value = LiveDataEvent(Route.Back)
    }
}