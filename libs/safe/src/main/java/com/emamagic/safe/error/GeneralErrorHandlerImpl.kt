package com.emamagic.safe.error

import android.database.sqlite.SQLiteException
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

abstract class GeneralErrorHandlerImpl : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException,
            is NoInternetException,
            is SocketException -> ErrorEntity.Network(message = "${throwable.message}")
            is SQLiteException -> ErrorEntity.Database(message = "${throwable.message}")
            is UnknownHostException,
            is ServerConnectionException -> ErrorEntity.Server(message = "${throwable.message}")
            is HttpException -> ErrorEntity.Api(
                message = throwable.messages,
                code = throwable.code,
                errorBody = throwable.errorBody
            )
            else -> ErrorEntity.Unknown(message = "${throwable.message}")
        }
    }

}