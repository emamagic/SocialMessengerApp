package com.emamagic.limoo

import androidx.multidex.MultiDexApplication
import com.emamagic.cache.cache.CacheInitializer
import com.emamagic.limoo.appinitializers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : MultiDexApplication() {
    @Inject lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        init()
        initializers.init(this)
    }

    private fun init() {
        CacheInitializer.init(this)

    }

}