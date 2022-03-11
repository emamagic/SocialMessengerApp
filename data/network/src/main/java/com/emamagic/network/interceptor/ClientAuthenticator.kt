package com.emamagic.network.interceptor

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Provider

class ClientAuthenticator @Inject constructor(
    private val persistentCookieJar: PersistentCookieJar,
    private val okHttpClient: Provider<OkHttpClient>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // TODO cancel other requestToken() call when token refreshed
        if (!refreshToken(persistentCookieJar, okHttpClient.get())) {
            logout()
            return null
        }
        return response.request
    }

    private fun refreshToken(
        persistentCookieJar: PersistentCookieJar,
        okHttpClient: OkHttpClient
    ): Boolean {
        synchronized(this) {
            val refreshToken = persistentCookieJar.getRefreshToken()!!
            clearCookies(persistentCookieJar)
            val jsonType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val jsonContent = JsonObject().toString()
            val body = jsonContent.toRequestBody(jsonType)
            val okhttpRequest = Request.Builder()
                .url("https://test.limonadapp.ir/Limonad/j_spring_jwt_security_check")
                .addHeader("Refresh", refreshToken)
                .post(body)
                .build()
            val response = okHttpClient.newBuilder().cookieJar(persistentCookieJar).build()
                .newCall(okhttpRequest).execute()
            return response.isSuccessful
        }
    }

    private fun logout() {
        // TODO throw custom exception
    }

    private fun clearCookies(persistentCookieJar: PersistentCookieJar) {
        persistentCookieJar.clear()
        persistentCookieJar.clearSession()
    }

}