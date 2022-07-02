package com.emamagic.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class HostSelectionInterceptor @Inject constructor() : Interceptor {

    @Volatile
    private var host: String? = null
    fun setHost(host: String?) {
        this.host = host
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val host = host
        if (host != null) {
            request = changeHost(request, host)
        }
        getOtpUrlOrNull(request)?.let { newRequest ->
            request = newRequest
        }
        return chain.proceed(request)
    }

    private fun getOtpUrlOrNull(request: Request): Request? {
        var encodedPath = request.url.encodedPath
        if (encodedPath.contains("j_spring_otptoken_security_check")) {
            encodedPath = "/Limonad${encodedPath.substring(15)}"
            val newUrl = request.url.newBuilder()
                .encodedPath(encodedPath)
                .build()
            return request.newBuilder().url(newUrl).build()
        }
        return null
    }

    private fun changeHost(request: Request, host: String): Request {
        val newUrl = request.url.newBuilder()
            .host(host)
            .build()
        return request.newBuilder()
            .url(newUrl)
            .build()
    }

}