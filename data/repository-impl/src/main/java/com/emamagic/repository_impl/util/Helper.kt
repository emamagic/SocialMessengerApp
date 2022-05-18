package com.emamagic.repository_impl.util

import android.util.Log
import com.emamagic.core.NoCurrentUserFoundException
import com.emamagic.core.ResultWrapper
import com.emamagic.core.ServerConnectionException
import com.emamagic.core.UserShouldNotBeLoginException
import com.emamagic.core.Error
import com.emamagic.safe.error.ErrorEntity
import com.emamagic.safe.error.HttpException
import com.emamagic.safe.util.SafeWrapper
import com.google.gson.Gson
import retrofit2.Response

fun <T> Response<T>.toResponse(): com.emamagic.safe.util.Response<T> =
    object : com.emamagic.safe.util.Response<T> {
        override fun isSuccessful(): Boolean =
            this@toResponse.isSuccessful

        override fun body(): T? =
            this@toResponse.body()

        override fun code(): Int =
            this@toResponse.code()

        override fun errorBody(): String? =
            this@toResponse.errorBody()?.string()
    }

fun ErrorEntity?.toError(): Error {
    var message: String? = "${this?.throwable?.message}  ${this?.throwable?.cause}"
    var displayMessage: String? = null
    var statusCode: Int? = null
    var error: Error? = null
    val errorType: String? = this!!::class.simpleName
    when (this) {
        is ErrorEntity.Api -> {
            (throwable as HttpException).errorBody?.let {
                try {
                    error =
                        Gson().fromJson((throwable as HttpException).errorBody, Error::class.java)
                    message = error?.message
                    displayMessage = error?.display_message
                    statusCode = error?.statusCode
                } catch (t: Throwable) {
                    message = "Wrong Error Model Sent"
                    displayMessage = "Error Happened"
                    statusCode = (this.throwable as HttpException).code
                }
            }
        }
        is ErrorEntity.Network -> {
            throwable =
                com.emamagic.core.NoInternetException("${throwable?.message}  ${throwable?.cause}")
        }
        is ErrorEntity.Server -> {
            throwable = ServerConnectionException("${throwable?.message}  ${throwable?.cause}")
        }
        else -> {
            when (throwable) {
                is NoCurrentUserFoundException -> {
                    Log.e("TAG", "toError: NoCurrentUserFoundException")
                }
                is UserShouldNotBeLoginException -> {
                    Log.e("TAG", "toError: UserShouldNotBeLoginException")
                }
            }
        }
    }

    return Error(
        id = error?.id,
        message = message,
        requestId = error?.requestId,
        statusCode = statusCode,
        isOauth = error?.isOauth,
        i18n_key = error?.i18n_key,
        display_message = displayMessage,
        errorType = errorType,
        throwable = throwable
    )
}

suspend fun <T,E> SafeWrapper<T>.toResult(
    onSuccess: (suspend (T) -> Unit)? = null,
    onFailed: (() -> Unit)? = null,
    shouldReturn: E? = null
): ResultWrapper<E> =
    when (this) {
        is SafeWrapper.Success -> {
            onSuccess?.invoke(data!!)
            val mData = shouldReturn ?: data!! as E
            ResultWrapper.Success(mData)
        }
        is SafeWrapper.Failed -> {
            onFailed?.invoke()
            ResultWrapper.Failed(error.toError())
        }
        is SafeWrapper.LoadingFetch -> TODO()
    }