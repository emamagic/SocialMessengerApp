package com.emamagic.navigation.fragment

import androidx.lifecycle.LiveData
import com.emamagic.navigation.lifecycle.LiveDataEvent
import com.emamagic.navigation.route.Route

interface RoutedViewModel {
    val iRoute: LiveData<LiveDataEvent<Route>>
}