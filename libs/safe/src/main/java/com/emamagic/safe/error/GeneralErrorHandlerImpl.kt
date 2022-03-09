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
            is SocketException -> ErrorEntity.Network(throwable = throwable)
            is SQLiteException -> ErrorEntity.Database(throwable = throwable)
            is UnknownHostException,
            is ServerConnectionException -> ErrorEntity.Server(throwable = throwable)
            is HttpException -> ErrorEntity.Api(
                throwable = throwable
            )
            else -> ErrorEntity.Unknown(throwable = throwable)
        }
    }

}