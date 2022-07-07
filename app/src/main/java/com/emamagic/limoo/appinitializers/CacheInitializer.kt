package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.cache.cache.CacheFactory
import com.emamagic.common_ui.appinitializer.AppInitializer
import javax.inject.Inject

class CacheInitializer @Inject constructor(): AppInitializer {
    override fun init(application: Application) {
        CacheFactory.create(application)
    }
}