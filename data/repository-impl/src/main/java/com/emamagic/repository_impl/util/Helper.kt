package com.emamagic.repository_impl.util

import com.emamagic.entity.Error
import com.emamagic.safe.error.ErrorEntity
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
    var message: String? = this?.message
    var displayMessage: String? = this?.message
    var statusCode: Int? = this?.code
    var error: Error? = null
    this?.errorBody?.let {
        try {
            error = Gson().fromJson(errorBody, Error::class.java)
            message = error?.message
            displayMessage = error?.display_message
            statusCode = error?.statusCode
        } catch (t: Throwable) {
            message = "Wrong Error Model Sent"
            displayMessage = "Wrong Error Model Sent"
        }
    }
    return Error(
        id = error?.id,
        message = message,
        requestId = error?.requestId,
        statusCode = statusCode,
        isOauth = error?.isOauth,
        i18n_key = error?.i18n_key,
        display_message = displayMessage
    )
}