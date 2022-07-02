package com.emamagic.core

sealed class ResultWrapper<T>(
    val data: T? = null,
    val error: Error? = null
) {
    class Success<T>(data: T) : ResultWrapper<T>(data = data)
    class Failed<T>(error: Error, data: T? = null) : ResultWrapper<T>(error = error, data = data)
    class FetchLoading<T>(data: T? = null) : ResultWrapper<T>(data = data)

    override fun toString(): String {
        return when (this) {
            is Success -> "Success [data=$data]"
            is Failed -> "Error[exception=${error?.message}]"
            is FetchLoading -> "LoadingFetch"
        }
    }
}

val ResultWrapper<*>.succeeded
    get() = this is ResultWrapper.Success && data != null
