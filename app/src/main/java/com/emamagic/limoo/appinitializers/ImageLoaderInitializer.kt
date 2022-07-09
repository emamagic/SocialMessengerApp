package com.emamagic.limoo.appinitializers

import android.app.Application
import coil.Coil
import coil.ImageLoader
import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.limoo.R
import dagger.Lazy
import okhttp3.OkHttpClient
import javax.inject.Inject

class ImageLoaderInitializer @Inject constructor(
    private val okHttpClient: OkHttpClient
): AppInitializer {
    override fun init(application: Application) {
        Coil.setImageLoader {
            ImageLoader.Builder(application).placeholder(R.drawable.ic_launcher_background).crossfade(true).okHttpClient(okHttpClient).build()
        }
    }
}