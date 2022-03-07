package com.emamagic.safe

import com.emamagic.safe.error.NoInternetException
import com.emamagic.safe.error.ServerConnectionException
import com.emamagic.safe.connectivity.Connectivity
import com.emamagic.safe.connectivity.ConnectivityPublisher
import com.emamagic.safe.error.ErrorEntity
import com.emamagic.safe.error.GeneralErrorHandlerImpl
import com.emamagic.safe.policy.CachePolicy
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.policy.RetryPolicy
import com.emamagic.safe.util.General
import com.emamagic.safe.util.Response
import com.emamagic.safe.util.ResultWrapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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

    inline fun <ResultType, RequestType> stream(
        cachePolicy: CachePolicy = CachePolicy(),
        crossinline localFetch: () -> Flow<ResultType>,
        retryPolicy: RetryPolicy = RetryPolicy(),
        crossinline remoteFetch: suspend () -> RequestType,
        crossinline mapping: (RequestType) -> ResultType,
        crossinline localStore: suspend (RequestType) -> Unit,
        crossinline localDelete: suspend () -> Unit,
        crossinline shouldFetch: (ResultType) -> Boolean = { true },
        crossinline onFetchSuccess: () -> Unit = { },
        crossinline onFetchFailed: (ErrorEntity) -> Unit = { }
    ) = channelFlow {

        when (cachePolicy.type) {
            CachePolicy.Type.NEVER -> {
                try {
                    send(ResultWrapper.Loading())
                    val response = retryIO(retryPolicy, this) { remoteFetch() }
                    onFetchSuccess()
                    send(ResultWrapper.Success(mapping(response)))
                } catch (t: Throwable) {
                    onFetchFailed(getError(t))
                    send(ResultWrapper.Failed(getError(t)))
                }
            }
            CachePolicy.Type.ALWAYS -> {
                val loading = launch {
                    localFetch().collect { send(ResultWrapper.Loading(it)) }
                }
                try {
                    localStore(retryIO(retryPolicy, this) { remoteFetch() })
                    onFetchSuccess()
                    loading.cancel()
                    localFetch().collect { send(ResultWrapper.Success(it)) }
                } catch (t: Throwable) {
                    onFetchFailed(getError(t))
                    loading.cancel()
                    localFetch().collect { send(ResultWrapper.Failed(getError(t), it)) }
                }
            }
            CachePolicy.Type.CLEAR -> {
                val data = localFetch().firstOrNull()
                if (data == null) {
                    try {
                        send(ResultWrapper.Loading())
                        val response = retryIO(retryPolicy, this) { remoteFetch() }
                        onFetchSuccess()
                        send(ResultWrapper.Success(mapping(response)))
                    } catch (t: Throwable) {
                        onFetchFailed(getError(t))
                        send(ResultWrapper.Failed(getError(t)))
                    }
                } else {
                    localFetch().collect { send(ResultWrapper.Success(it)) }
                    localDelete()
                }
            }
            CachePolicy.Type.REFRESH -> {
                try {
                    localStore(retryIO(retryPolicy, this) { remoteFetch() })
                    localFetch().collect { send(ResultWrapper.Success(it)) }
                } catch (t: Throwable) {
                    onFetchFailed(getError(t))
                    send(ResultWrapper.Failed(getError(t)))
                }
            }
            CachePolicy.Type.EXPIRES -> {
                if ((cachePolicy.createAt + cachePolicy.expires) > System.currentTimeMillis()) {
                    localFetch().collect { send(ResultWrapper.Success(it)) }
                } else {
                    val loading = launch {
                        localFetch().collect { send(ResultWrapper.Loading(it)) }
                    }
                    try {
                        localStore(retryIO(retryPolicy, null) { remoteFetch() })
                        onFetchSuccess()
                        loading.cancel()
                        localFetch().collect { send(ResultWrapper.Success(it)) }
                    } catch (t: Throwable) {
                        onFetchFailed(getError(t))
                        loading.cancel()
                        localFetch().collect { send(ResultWrapper.Failed(getError(t), it)) }
                    }
                }
            }
            CachePolicy.Type.CUSTOM -> {
                // it could throw exception
                val data = localFetch().first()

                if (shouldFetch(data)) {
                    val loading = launch {
                        localFetch().collect { send(ResultWrapper.Loading(it)) }
                    }
                    try {
                        localStore(retryIO(retryPolicy, null) { remoteFetch() })
                        onFetchSuccess()
                        loading.cancel()
                        localFetch().collect { send(ResultWrapper.Success(it)) }
                    } catch (t: Throwable) {
                        onFetchFailed(getError(t))
                        loading.cancel()
                        localFetch().collect { send(ResultWrapper.Failed(getError(t), it)) }
                    }
                } else {
                    localFetch().collect { send(ResultWrapper.Success(it)) }
                }
            }
            else -> throw Exception("This cache policy does not implemented")
        }
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

    suspend fun <T> retryIO(
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