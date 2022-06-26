package com.emamagic.base.appinitializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}
