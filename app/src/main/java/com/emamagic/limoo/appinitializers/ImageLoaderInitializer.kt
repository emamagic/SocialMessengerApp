package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.image_loader.AppImageLoaderFactory
import dagger.Lazy
import okhttp3.OkHttpClient
import javax.inject.Inject

class ImageLoaderInitializer @Inject constructor(
    private val okHttpClient: Lazy<OkHttpClient>
): AppInitializer {
    override fun init(application: Application) {
        AppImageLoaderFactory.create(application, okHttpClient.get())
    }
}