package com.emamagic.core_android

fun String.isValidPhoneNumber(): Boolean =
    isNotEmpty() && ((length == 10 && first() == '9') || (length == 11 && first() == '0'))
