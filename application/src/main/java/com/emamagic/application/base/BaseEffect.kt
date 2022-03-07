package com.emamagic.application.base

import androidx.navigation.NavDirections
import com.emamagic.application.utils.toast.ToastyMode

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
        val message: String,
        @ToastyMode val mode: Int = ToastyMode.MODE_TOAST_DEFAULT
    ) : BaseEffect

    data class ShowLoading(val isDim: Boolean = false) : BaseEffect

    object HideLoading : BaseEffect

    data class NavigateTo(val directions: NavDirections) : BaseEffect

    object NavigateBack : BaseEffect

    object HideKeyboard : BaseEffect

    object EnableUiComponent : BaseEffect

    object DisableUiComponent : BaseEffect

    object NeedToSignUp: BaseEffect

}

/*-------------------------------------- CUSTOM EFFECT FOR EVERY FEATURE --------------------------------------*/

sealed class HomeEffect : BaseEffect {
    object StopShimmer : HomeEffect()
}
