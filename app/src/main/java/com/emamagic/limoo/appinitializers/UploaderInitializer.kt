package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.uploader.UploaderFactory
import dagger.Lazy
import okhttp3.OkHttpClient
import java.util.concurrent.ThreadPoolExecutor
import javax.inject.Inject

class UploaderInitializer @Inject constructor(
    private val okHttpClient: Lazy<OkHttpClient>,
    private val threadPoolExecutor: ThreadPoolExecutor
): AppInitializer {
    override fun init(application: Application) {
        UploaderFactory.create(application, okHttpClient.get(), threadPoolExecutor)
    }
}