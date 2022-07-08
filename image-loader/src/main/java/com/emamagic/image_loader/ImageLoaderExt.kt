package com.emamagic.image_loader

import android.content.Context
import android.widget.ImageView
import coil.Coil
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.util.CoilUtils

inline val Context.imageLoader: ImageLoader
    get() = Coil.imageLoader(this)

inline fun ImageView.load(
    data: Any?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable {
    val request = ImageRequest.Builder(context)
        .data(data)
        .target(this)
        .apply(builder)
        .build()
    return imageLoader.enqueue(request)
}

fun ImageView.dispose() {
    CoilUtils.dispose(this)
}

inline val ImageView.result: ImageResult?
    get() = CoilUtils.result(this)
