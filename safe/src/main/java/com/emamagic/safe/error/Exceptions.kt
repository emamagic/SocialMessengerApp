package com.emamagic.safe.error

import java.io.IOException

class NoInternetException(message: String? = null): IOException(message)
class ServerConnectionException(message: String? = null): IOException(message)
data class HttpException(
    val code: Int,
    val messages: String?,
    val errorBody: String?
): IOException(messages)