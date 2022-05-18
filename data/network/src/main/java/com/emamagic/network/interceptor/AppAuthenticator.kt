package com.emamagic.network.interceptor

import com.emamagic.network.publisher.Event
import com.emamagic.network.publisher.NotificationCenter
import com.emamagic.network.util.Const
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Provider

class AppAuthenticator @Inject constructor(
    private val cookiePersistor: CookiePersistor,
    private val okHttpClient: Provider<OkHttpClient>
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // TODO cancel other requestToken() call when token refreshed
        if (!refreshToken(cookiePersistor, okHttpClient.get())) {
            logout()
            return null
        }
        return response.request
    }

    private fun refreshToken(
        cookiePersistor: CookiePersistor,
        okHttpClient: OkHttpClient
    ): Boolean = synchronized(this) {
        cookiePersistor.getRefreshToken()?.let { refreshToken ->
                val jsonType = "application/json; charset=utf-8".toMediaTypeOrNull()
                val jsonContent = JsonObject().toString()
                val body = jsonContent.toRequestBody(jsonType)
                val okhttpRequest = Request.Builder()
                    .url("https://test.limonadapp.ir/Limonad/j_spring_jwt_security_check")
                    .addHeader("Refresh", refreshToken)
                    .post(body)
                    .build()
                val response = okHttpClient
                    .newBuilder()
                    .build()
                    .newCall(okhttpRequest).execute()
                return response.isSuccessful
            }
            return@synchronized false
        }


    private fun logout() {
        NotificationCenter.notifySubscribers(Event(Const.LOGGED_OUT))
    }

}