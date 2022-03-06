package com.emamagic.common_jvm

interface Result<T>

inline fun <reified T> Result<T>.onSuccess(action: (success: T) -> Unit) {
    if (this is T) action.invoke(this)
}
