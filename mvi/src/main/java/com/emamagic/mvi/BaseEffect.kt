package com.emamagic.mvi

import androidx.annotation.StringRes
import androidx.navigation.NavDirections

sealed interface BaseEffect {

    data class Dialog(
        val message: String,
        val proceedTitle: String?,
        val cancelTitle: String?
    ) : BaseEffect

    data class SnackBar(
        val message: String,
        val timeOut: Long? = null
    ) : BaseEffect

    data class Toast(
        val message: String
    ) : BaseEffect

    data class ShowLoading(val isDim: Boolean = false, val type: Any? = null) : BaseEffect

    data class HideLoading(val type: Any? = null) : BaseEffect

    data class NavigateTo(val directions: NavDirections) : BaseEffect

    object NavigateBack : BaseEffect

    object HideKeyboard : BaseEffect

    data class EnableUiComponent(val type: Any? = null) : BaseEffect

    data class DisableUiComponent(val type: Any? = null) : BaseEffect

    object NeedToSignUp : BaseEffect

    object NeedToSignIn : BaseEffect

    sealed class InvalidInput : BaseEffect {
        data class Error(val message: String? = null, val type: Any? = null) : InvalidInput()
        data class Error2(@StringRes val resId: Int? = null, val type: Any? = null) : InvalidInput()
    }
}
/*-------------------------------------- CUSTOM EFFECT FOR EVERY FEATURE --------------------------------------*/

sealed class LoginEffect : BaseEffect {
    data class Keycloak(
        val authorizationEndpoint: String,
        val tokenEndpoint: String,
        val keycloakRedirectUri: String,
        val keycloakResource: String,
        val keycloakScope: String,
        val responseType: String
    ) : LoginEffect()

    data class PerformAuthorization(
        val authorizationEndpoint: String,
        val tokenEndpoint: String,
        val keycloakRedirectUri: String,
        val keycloakResource: String,
        val keycloakScope: String,
        val responseType: String
    ) : LoginEffect()
}
