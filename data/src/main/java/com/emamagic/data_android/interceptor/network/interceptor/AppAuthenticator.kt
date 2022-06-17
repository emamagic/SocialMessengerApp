package com.emamagic.data_android.interceptor.network.interceptor

import com.emamagic.data_android.interceptor.Const
import com.emamagic.data_android.interceptor.network.RestProvider
import com.emamagic.domain.publisher.Event
import com.emamagic.domain.publisher.NotificationCenter
import com.franmontiel.persistentcookiejar.ClearableCookieJar
import dagger.Lazy
import okhttp3.*
import javax.inject.Inject

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