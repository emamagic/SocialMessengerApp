package com.emamagic.repository_impl.util

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.common_jvm.ServerConnectionException
import com.emamagic.entity.Error
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
                com.emamagic.common_jvm.NoInternetException("${throwable?.message}  ${throwable?.cause}")
        }
        is ErrorEntity.Server -> {
            throwable = ServerConnectionException("${throwable?.message}  ${throwable?.cause}")
        }
        else -> { /* Do Nothing */
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

fun <T,E> SafeWrapper<T>.toResult(
    onSuccess: ((T) -> Unit)? = null,
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