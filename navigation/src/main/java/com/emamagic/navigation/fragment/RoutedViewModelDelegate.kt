package com.emamagic.navigation.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.emamagic.navigation.lifecycle.LiveDataEvent
import com.emamagic.navigation.route.Route

class RoutedViewModelDelegate<ROUTE : Route> : RoutedViewModel {

    private val _route = MutableLiveData<LiveDataEvent<Route>>()
    override val iRoute: LiveData<LiveDataEvent<Route>>
        get() = _route

    fun pushRoute(route: ROUTE) {
        try {
            _route.value = LiveDataEvent(route)
        } catch (t: Throwable) {
            Log.e("TAG", "pushRoute: ${t.stackTraceToString()}")
        }
    }

    fun popRoute() {
        try {
            _route.value = LiveDataEvent(Route.Back)
        } catch (t: Throwable) {
            Log.e("TAG", "popRoute: ${t.stackTraceToString()}")
        }
    }
}