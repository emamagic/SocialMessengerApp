package com.emamagic.data.syncers

import com.emamagic.core.Error
import com.emamagic.core.ResultWrapper
import com.emamagic.data.toError
import com.emamagic.safe.error.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

inline fun <ResultType, RequestType> networkBoundResource(
    errorHandler: ErrorHandler,
    crossinline databaseQuery: () -> Flow<ResultType>,
    crossinline networkCall: suspend () -> RequestType,
    crossinline saveCallResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Error) -> Unit = { }
) = channelFlow {
    val data = databaseQuery().first()
    if (shouldFetch(data)) {
        val loading = launch {
            databaseQuery().collect { send(ResultWrapper.FetchLoading(it)) }
        }

        try {
            saveCallResult(networkCall())
            onFetchSuccess()
            loading.cancel()
            databaseQuery().collect { send(ResultWrapper.Success(it)) }
        } catch (t: Throwable) {
            onFetchFailed(errorHandler.getError(t).toError())
            loading.cancel()
            databaseQuery().collect { send(ResultWrapper.Failed(errorHandler.getError(t).toError(), it)) }
        }
    } else {
        databaseQuery().collect { send(ResultWrapper.Success(it)) }
    }
}