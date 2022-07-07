package com.emamagic.mvi


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

    data class ShowLoading(val isDim: Boolean = false, val type: Any? = null, val scope: Int = 0) : BaseEffect

    data class HideLoading(val type: Any? = null, val scope: Int = 0) : BaseEffect

    object HideKeyboard : BaseEffect

    data class EnableUiComponent(val type: Any? = null) : BaseEffect

    data class DisableUiComponent(val type: Any? = null) : BaseEffect

    object NeedToLogin : BaseEffect

    data class InvalidInput(val message: String? = null, val resId: Int? = null, val type: Any? = null) : BaseEffect

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
