package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.androidcore.appinitializer.AppInitializer
import com.emamagic.androidcore.helpers.TypeFaceHelper
import javax.inject.Inject

class TypeFaceInitializer @Inject constructor(): AppInitializer {
    override fun init(application: Application) {
        TypeFaceHelper.init(application)
    }
}