package com.emamagic.mvi

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

    data class ShowLoading(val isDim: Boolean = false) : BaseEffect

    object HideLoading : BaseEffect

    data class NavigateTo(val directions: NavDirections) : BaseEffect

    object NavigateBack : BaseEffect

    object HideKeyboard : BaseEffect

    object EnableUiComponent : BaseEffect

    object DisableUiComponent : BaseEffect

    object NeedToSignUp: BaseEffect

    object NeedToSignIn: BaseEffect

}

/*-------------------------------------- CUSTOM EFFECT FOR EVERY FEATURE --------------------------------------*/

sealed class SigninEffect : BaseEffect {
    object InvalidPhoneNumber : SigninEffect()
    object InvalidOtpCode: SigninEffect()
    object InvalidUsername: SigninEffect()
    object InvalidPass: SigninEffect()
}
