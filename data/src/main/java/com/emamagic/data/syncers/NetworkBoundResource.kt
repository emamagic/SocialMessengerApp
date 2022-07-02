package com.emamagic.data.syncers

import android.util.Log
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
    Log.e("TAG", "networkBoundResource: 0", )
    val data = databaseQuery().first()
    if (shouldFetch(data)) {
        val loading = launch {
            databaseQuery().collect { send(ResultWrapper.FetchLoading(it)) }
        }

        Log.e("TAG", "networkBoundResource: 1", )
        try {
            saveCallResult(networkCall())
            Log.e("TAG", "networkBoundResource: 2", )
            onFetchSuccess()
            loading.cancel()
            Log.e("TAG", "networkBoundResource: 3", )
            databaseQuery().collect { send(ResultWrapper.Success(it)) }
        } catch (t: Throwable) {
            Log.e("TAG", "networkBoundResource: 4", )
            onFetchFailed(errorHandler.getError(t).toError())
            loading.cancel()
            databaseQuery().collect { send(ResultWrapper.Failed(errorHandler.getError(t).toError(), it)) }
        }
    } else {
        databaseQuery().collect { send(ResultWrapper.Success(it)) }
    }
}