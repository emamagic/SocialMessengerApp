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
import com.emamagic.safe.util.SafeWrapper
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
        memoryPolicy: MemoryPolicy<ResultType>,
        remoteFetch: suspend () -> Response<ResultType>
    ): SafeWrapper<ResultType> =
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
        memoryPolicy: MemoryPolicy<ResultType>,
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType,
    ): SafeWrapper<ResultType> =
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
    ): SafeWrapper<ResultType> =
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
    ): SafeWrapper<ResultType> =
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
                    send(SafeWrapper.LoadingFetch())
                    val response = retryIO(retryPolicy, this) { remoteFetch() }
                    onFetchSuccess()
                    send(SafeWrapper.Success(mapping(response)))
                } catch (t: Throwable) {
                    onFetchFailed(getError(t))
                    send(SafeWrapper.Failed(getError(t)))
                }
            }
            CachePolicy.Type.ALWAYS -> {
                val loading = launch {
                    localFetch().collect { send(SafeWrapper.LoadingFetch(it)) }
                }
                try {
                    localStore(retryIO(retryPolicy, this) { remoteFetch() })
                    onFetchSuccess()
                    loading.cancel()
                    localFetch().collect { send(SafeWrapper.Success(it)) }
                } catch (t: Throwable) {
                    onFetchFailed(getError(t))
                    loading.cancel()
                    localFetch().collect { send(SafeWrapper.Failed(getError(t), it)) }
                }
            }
            CachePolicy.Type.CLEAR -> {
                val data = localFetch().firstOrNull()
                if (data == null) {
                    try {
                        send(SafeWrapper.LoadingFetch())
                        val response = retryIO(retryPolicy, this) { remoteFetch() }
                        onFetchSuccess()
                        send(SafeWrapper.Success(mapping(response)))
                    } catch (t: Throwable) {
                        onFetchFailed(getError(t))
                        send(SafeWrapper.Failed(getError(t)))
                    }
                } else {
                    localFetch().collect { send(SafeWrapper.Success(it)) }
                    localDelete()
                }
            }
            CachePolicy.Type.REFRESH -> {
                try {
                    localStore(retryIO(retryPolicy, this) { remoteFetch() })
                    localFetch().collect { send(SafeWrapper.Success(it)) }
                } catch (t: Throwable) {
                    onFetchFailed(getError(t))
                    send(SafeWrapper.Failed(getError(t)))
                }
            }
            CachePolicy.Type.EXPIRES -> {
                if ((cachePolicy.createAt + cachePolicy.expires) > System.currentTimeMillis()) {
                    localFetch().collect { send(SafeWrapper.Success(it)) }
                } else {
                    val loading = launch {
                        localFetch().collect { send(SafeWrapper.LoadingFetch(it)) }
                    }
                    try {
                        localStore(retryIO(retryPolicy, null) { remoteFetch() })
                        onFetchSuccess()
                        loading.cancel()
                        localFetch().collect { send(SafeWrapper.Success(it)) }
                    } catch (t: Throwable) {
                        onFetchFailed(getError(t))
                        loading.cancel()
                        localFetch().collect { send(SafeWrapper.Failed(getError(t), it)) }
                    }
                }
            }
            CachePolicy.Type.CUSTOM -> {
                // it could throw exception
                val data = localFetch().first()

                if (shouldFetch(data)) {
                    val loading = launch {
                        localFetch().collect { send(SafeWrapper.LoadingFetch(it)) }
                    }
                    try {
                        localStore(retryIO(retryPolicy, null) { remoteFetch() })
                        onFetchSuccess()
                        loading.cancel()
                        localFetch().collect { send(SafeWrapper.Success(it)) }
                    } catch (t: Throwable) {
                        onFetchFailed(getError(t))
                        loading.cancel()
                        localFetch().collect { send(SafeWrapper.Failed(getError(t), it)) }
                    }
                } else {
                    localFetch().collect { send(SafeWrapper.Success(it)) }
                }
            }
            else -> throw Exception("This cache policy does not implemented")
        }
    }
    

    private inline fun <ResultType> handleResponse(
        key: String? = null,
        memoryPolicy: MemoryPolicy<ResultType>? = null,
        call: () -> Response<ResultType>): SafeWrapper<ResultType> {
        if (key != null)
            General.cache[key]?.let { result -> return result as SafeWrapper<ResultType> }
        var safe: SafeWrapper<ResultType>
        return try {
            val response = call()
            if (response.isSuccessful()) {
                response.body()?.let {
                    safe = SafeWrapper.Success(
                        data = it,
                        code = response.code()
                    )
                    key?.let { key -> General.cache.put(key, safe, memoryPolicy!!.isExpired()) }
                    return safe
                }
            }
            safe = SafeWrapper.Failed(
                error = ErrorEntity.Api(response.errorBody()),
            )
            return safe
        } catch (t: Throwable) {
            safe = SafeWrapper.Failed(getError(t))
            safe
        }
    }


    private inline fun <RequestType, ResultType> handleResponse(
        key: String? = null,
        memoryPolicy: MemoryPolicy<ResultType>? = null,
        call: () -> Response<RequestType>,
        noinline converter: (RequestType) -> ResultType
    ): SafeWrapper<ResultType> {
        if (key != null)
            General.cache[key]?.let { result -> return result as SafeWrapper<ResultType> }
        var safe: SafeWrapper<ResultType>
        return try {
            val response = call()
            if (response.isSuccessful()) {
                response.body()?.let {
                    safe = SafeWrapper.Success(
                        data = converter(it),
                        code = response.code()
                    )
                    key?.let { key -> General.cache.put(key, safe , memoryPolicy!!.isExpired()) }
                    return safe
                }
            }
            safe = SafeWrapper.Failed(
                error = ErrorEntity.Api(response.errorBody()),
            )
            return safe
        } catch (t: Throwable) {
            safe = SafeWrapper.Failed(getError(t))
            safe
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