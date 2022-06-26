package com.emamagic.login

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface LoginRouter: Router {

    sealed class Routes : Route {
        object LoginViaPhoneNumberToLoginViaUsername: Routes()
        object LoginViaPhoneNumberToChangeServer: Routes()
        object LoginViaPhoneNumberToOtp: Routes()
        object OtpToConversations: Routes()
        object UsernameToConversations: Routes()
        object ChangeServerToConversations: Routes()
    }


}