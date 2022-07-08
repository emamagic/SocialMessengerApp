package com.emamagic.image_loader

import android.app.Application
import android.content.Context
import coil.Coil
import coil.ImageLoader
import okhttp3.OkHttpClient

object AppImageLoaderFactory {

    fun create(application: Application, client: OkHttpClient) {
        Coil.setImageLoader {
            ImageLoader.Builder(application).crossfade(true).okHttpClient(client).build()
        }
    }

}