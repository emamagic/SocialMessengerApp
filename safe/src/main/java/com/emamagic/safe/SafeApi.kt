package com.emamagic.safe

import com.emamagic.safe.error.NoInternetException
import com.emamagic.safe.error.ServerConnectionException
import com.emamagic.safe.connectivity.Connectivity
import com.emamagic.safe.connectivity.ConnectivityPublisher
import com.emamagic.safe.error.ErrorEntity
import com.emamagic.safe.error.GeneralErrorHandlerImpl
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.policy.RetryPolicy
import com.emamagic.safe.util.General
import com.emamagic.safe.util.Response
import com.emamagic.safe.util.ResultWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.withLock
import java.io.IOException

abstract class SafeApi : GeneralErrorHandlerImpl() {

    override suspend fun <ResultType> get(
        key: String,
        retryPolicy: RetryPolicy,
        memoryPolicy: MemoryPolicy,
        remoteFetch: suspend () -> Response<ResultType>
    ): ResultWrapper<ResultType> =
        withContext(Dispatchers.IO) {
            handleResponse (key = key, memoryPolicy = memoryPolicy) {
                retryIO(
                    retryPolicy,
                    this
                ) { remoteFetch() }
            }
        }

    override suspend fun <ResultType, RequestType> get(
        key: String,
        retryPolicy: RetryPolicy,
        memoryPolicy: MemoryPolicy,
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType,
    ): ResultWrapper<ResultType> =
        withContext(Dispatchers.IO) {
            handleResponse(key = key, memoryPolicy = memoryPolicy, {
                retryIO(
                    retryPolicy,
                    this
                ) { remoteFetch() }
            }, mapping)
        }

    override suspend fun <ResultType> fresh(
        retryPolicy: RetryPolicy,
        remoteFetch: suspend () -> Response<ResultType>
    ): ResultWrapper<ResultType> =
        withContext(Dispatchers.IO) {
            handleResponse {
                retryIO(
                    retryPolicy,
                    this
                ) { remoteFetch() }
            }
        }

    override suspend fun <ResultType, RequestType> fresh(
        retryPolicy: RetryPolicy,
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType
    ): ResultWrapper<ResultType> =
        withContext(Dispatchers.IO) {
            handleResponse(key = null, memoryPolicy = null, {
                retryIO(
                    retryPolicy,
                    this
                ) { remoteFetch() }
            }, mapping)
        }


    private inline fun <ResultType> handleResponse(
        key: String? = null,
        memoryPolicy: MemoryPolicy? = null,
        call: () -> Response<ResultType>): ResultWrapper<ResultType> {
        if (key != null)
            General.cache[key]?.let { result -> return result as ResultWrapper<ResultType> }
        var result: ResultWrapper<ResultType>
        return try {
            val response = call()
            if (response.isSuccessful()) {
                response.body()?.let {
                    result = ResultWrapper.Success(
                        data = it,
                        code = response.code()
                    )
                    key?.let { key -> General.cache.put(key, result, memoryPolicy) }
                    return result
                }
            }
            result = ResultWrapper.Failed(
                error = ErrorEntity.Api(response.errorBody()),
            )
            return result
        } catch (t: Throwable) {
            result = ResultWrapper.Failed(getError(t))
            result
        }
    }


    private inline fun <RequestType, ResultType> handleResponse(
        key: String? = null,
        memoryPolicy: MemoryPolicy? = null,
        call: () -> Response<RequestType>,
        noinline converter: (RequestType) -> ResultType
    ): ResultWrapper<ResultType> {
        if (key != null)
            General.cache[key]?.let { result -> return result as ResultWrapper<ResultType> }
        var result: ResultWrapper<ResultType>
        return try {
            val response = call()
            if (response.isSuccessful()) {
                response.body()?.let {
                    result = ResultWrapper.Success(
                        data = converter(it),
                        code = response.code()
                    )
                    key?.let { key -> General.cache.put(key, result , memoryPolicy) }
                    return result
                }
            }
            result = ResultWrapper.Failed(
                error = ErrorEntity.Api(response.errorBody()),
            )
            return result
        } catch (t: Throwable) {
            result = ResultWrapper.Failed(getError(t))
            result
        }

    }

    private suspend fun <T> retryIO(
        retryPolicy: RetryPolicy = RetryPolicy(),
        coroutineScope: CoroutineScope?,
        block: suspend () -> T
    ): T = General.getMutex.withLock {
        if (!General.shouldRetryNetworkCall) coroutineScope?.cancel() // disable retryIo api call

        var currentDelay = retryPolicy.initialDelay
        repeat(retryPolicy.times - 1) { index ->
            try {
                return block()
            } catch (e: IOException) {
                if (index == retryPolicy.times - 2 && (e is NoInternetException || e is ServerConnectionException)) {
                    ConnectivityPublisher.notifySubscribers(Connectivity(General.DISCONNECT))
                }
            }
            if (General.shouldRetryNetworkCall) {
                delay(currentDelay)
                currentDelay =
                    (currentDelay * retryPolicy.factor).toLong().coerceAtMost(retryPolicy.maxDelay)
            }
        }
        return block()
    }

}