@file:Suppress("UNCHECKED_CAST")

package com.emamagic.data

import android.util.Log
import com.emamagic.core.NoCurrentUserFoundException
import com.emamagic.core.ResultWrapper
import com.emamagic.core.ServerConnectionException
import com.emamagic.core.UserShouldNotBeLoginException
import com.emamagic.safe.error.ErrorEntity
import com.emamagic.safe.error.HttpException
import com.emamagic.safe.util.SafeWrapper
import com.google.gson.Gson
import retrofit2.Response
import com.emamagic.core.Error
import com.emamagic.safe.util.IResponse

fun <T> Response<T>.toResponse(): IResponse<T> =
    object : IResponse<T> {
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
            if (throwable is HttpException) {
                try {
                    error =
                        Gson().fromJson(
                            (throwable as HttpException).errorBody,
                            Error::class.java
                        )
                    message = error?.message
                    displayMessage = error?.display_message
                    statusCode = error?.status_code
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
        status_code = statusCode,
        isOauth = error?.isOauth,
        i18n_key = error?.i18n_key,
        display_message = displayMessage,
        errorType = errorType,
        throwable = throwable
    )
}

suspend fun <T, E> SafeWrapper<T>.toResult(
    doOnSuccess: (suspend (T) -> Unit)? = null,
    doOnFailed: ((Error) -> Unit)? = null,
    tryIfFailed: (suspend () -> E?)? = null,
    shouldReturn: E? = null
): ResultWrapper<E> =
    when (this) {
        is SafeWrapper.Success -> {
            Log.e("TAG", "toResult: Success")
            doOnSuccess?.invoke(data!!)
            val mData = shouldReturn ?: data!! as E
            ResultWrapper.Success(mData)
        }
        is SafeWrapper.Failed -> {
            val error = error.toError()
            Log.e("TAG", "toResult: Failed")
            if (tryIfFailed != null) {
                val newResult = tryIfFailed()
                if (newResult == null) {
                    doOnFailed?.invoke(error)
                    ResultWrapper.Failed(error)
                } else {
                    ResultWrapper.Success(newResult)
                }
            } else {
                doOnFailed?.invoke(error)
                ResultWrapper.Failed(error)
            }
        }
        is SafeWrapper.LoadingFetch -> TODO()
    }