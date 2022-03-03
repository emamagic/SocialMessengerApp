package com.emamagic.application.utils.toast

import androidx.annotation.DrawableRes
import com.emamagic.application.R
import java.lang.Exception

enum class ToastyType(
    @ToastyMode val toastyMode: Int,
    @DrawableRes val background: Int,
    @DrawableRes val icon: Int

) {
    SUCCESS(
        background = R.drawable.bg_corroct_toast,
        icon = R.drawable.ic_corroct_toast,
        toastyMode = ToastyMode.MODE_TOAST_SUCCESS
    ),
    WARNING(
        background = R.drawable.bg_warning_toast,
        icon = R.drawable.ic_warning_toast,
        toastyMode = ToastyMode.MODE_TOAST_WARNING
    ),
    ERROR(
        background = R.drawable.bg_error_toast,
        icon = R.drawable.ic_error_toast,
        toastyMode = ToastyMode.MODE_TOAST_ERROR
    )
}

fun properToastyType(@ToastyMode toastyMode: Int): ToastyType {
   return ToastyType.values().find { it.toastyMode == toastyMode } ?: throw Exception("ToastyType -> undefine toasty mode")
}