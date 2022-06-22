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

    data class ShowLoading(val isDim: Boolean = false, val type: Any? = null) : BaseEffect

    data class HideLoading(val type: Any? = null) : BaseEffect

    data class NavigateTo(val directions: NavDirections) : BaseEffect

    object NavigateBack : BaseEffect

    object HideKeyboard : BaseEffect

    data class EnableUiComponent(val type: Any? = null) : BaseEffect

    data class DisableUiComponent(val type: Any? = null) : BaseEffect

    object NeedToSignUp: BaseEffect

    object NeedToSignIn: BaseEffect

    data class EmptyInputValue(val type: Any? = null): BaseEffect

    data class InvalidInputValue(val message: String? = null ,val type: Any? = null): BaseEffect

}
