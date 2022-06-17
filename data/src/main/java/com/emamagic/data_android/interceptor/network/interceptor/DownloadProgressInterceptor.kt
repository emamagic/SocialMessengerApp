package com.emamagic.data_android.interceptor.network.interceptor

import com.emamagic.data_android.interceptor.network.DownloadProgressResponseBody
import okhttp3.Interceptor
import okhttp3.Response

class DownloadProgressInterceptor constructor(
    private val downloadProgressResponseBody: DownloadProgressResponseBody
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        downloadProgressResponseBody.setResponseBody(originalResponse.body)
        return originalResponse.newBuilder().body(downloadProgressResponseBody).build()
    }
}