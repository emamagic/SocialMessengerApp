package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.base.appinitializer.AppInitializer
import javax.inject.Inject

class AppInitializers @Inject constructor(
    private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
    fun init(application: Application) {
        initializers.forEach { it.init(application) }
    }

}