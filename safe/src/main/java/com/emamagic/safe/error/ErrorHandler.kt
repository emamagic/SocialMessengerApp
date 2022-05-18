package com.emamagic.safe.error

import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.policy.RetryPolicy
import com.emamagic.safe.util.Response
import com.emamagic.safe.util.SafeWrapper

interface ErrorHandler {

    suspend fun <ResultType> get(
        key: String,
        retryPolicy: RetryPolicy = RetryPolicy(),
        memoryPolicy: MemoryPolicy<ResultType> = MemoryPolicy<ResultType>(),
        remoteFetch: suspend () -> Response<ResultType>
    ): SafeWrapper<ResultType>

    suspend fun <ResultType, RequestType> get(
        key: String,
        retryPolicy: RetryPolicy = RetryPolicy(),
        memoryPolicy: MemoryPolicy<ResultType> = MemoryPolicy<ResultType>(),
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType
    ): SafeWrapper<ResultType>

    suspend fun <ResultType> fresh(
        retryPolicy: RetryPolicy = RetryPolicy(),
        remoteFetch: suspend () -> Response<ResultType>): SafeWrapper<ResultType>

    suspend fun <ResultType, RequestType> fresh(
        retryPolicy: RetryPolicy = RetryPolicy(),
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType
    ): SafeWrapper<ResultType>

    fun getError(throwable: Throwable): ErrorEntity

}