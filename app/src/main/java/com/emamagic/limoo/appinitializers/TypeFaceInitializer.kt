package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.common_ui.helpers.TypeFaceFactory
import javax.inject.Inject

class TypeFaceInitializer @Inject constructor(): AppInitializer {
    override fun init(application: Application) {
        TypeFaceFactory.create(application)
    }
}