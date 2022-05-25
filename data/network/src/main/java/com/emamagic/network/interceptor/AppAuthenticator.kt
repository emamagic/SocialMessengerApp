package com.emamagic.network.interceptor

import com.emamagic.network.RestProvider
import com.emamagic.network.publisher.Event
import com.emamagic.network.publisher.NotificationCenter
import com.emamagic.network.util.Const
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor
import dagger.Lazy
import okhttp3.*
import javax.inject.Inject
import javax.inject.Provider

class AppAuthenticator @Inject constructor(
    private val restProvider: Lazy<RestProvider>,
    private val cookieJar: ClearableCookieJar
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        // TODO cancel other requestToken() call when token refreshed
        if (!refreshAccessToken(cookieJar.refreshToken)) {
            logout()
            return null
        }
        return response.request
    }

    private fun refreshAccessToken(refreshToken: String?): Boolean = synchronized(this) {
        if (!refreshToken.isNullOrEmpty()) {
            return@synchronized restProvider.get().getAccessTokenRefresherCall(refreshToken).execute().isSuccessful
        }
            return@synchronized false
        }


    private fun logout() {
        cookieJar.clear()
        NotificationCenter.notifySubscribers(Event(Const.LOGGED_OUT))
    }

}