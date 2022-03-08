package com.emamagic.common_jvm

import com.emamagic.entity.Error

sealed class ResultWrapper<T>(
    val data: T? = null,
    val error: Error? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data = data)
    class Failed<T>(error: Error, data: T? = null) : ResultWrapper<T>(error = error, data = data)
    object Loading : ResultWrapper<Any>()
    class LoadingFetch<T>(data: T? = null) : ResultWrapper<T>(data = data)

    override fun toString(): String {
        return when (this) {
            is Success -> "Success [data=$data]"
            is Failed -> "Error[exception=${error?.message}]"
            is LoadingFetch -> "LoadingFetch"
            Loading -> "Loading"
        }
    }
}

val ResultWrapper<*>.succeeded
    get() = this is ResultWrapper.Success && data != null
