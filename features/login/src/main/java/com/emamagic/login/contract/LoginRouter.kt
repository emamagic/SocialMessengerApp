package com.emamagic.login.contract

import com.emamagic.navigation.route.Route
import com.emamagic.navigation.router.Router

interface LoginRouter: Router {

    sealed class Routes : Route {
        object LoginViaPhoneNumberToLoginViaUsername: Routes()
        object LoginViaPhoneNumberToChangeServer: Routes()
        object LoginViaPhoneNumberToOtp: Routes()
        object ToConversations: Routes()
        object ToSignup: Routes()
        object ToWorkspaceSelect: Routes()
        object ToWorkspaceCreate: Routes()
    }


}