package com.emamagic.safe.error

import com.emamagic.common_jvm.ErrorEntity
import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.policy.RetryPolicy
import retrofit2.Response

interface ErrorHandler {

    suspend fun <ResultType> get(
        key: String,
        retryPolicy: RetryPolicy = RetryPolicy(),
        memoryPolicy: MemoryPolicy = MemoryPolicy(),
        remoteFetch: suspend () -> Response<ResultType>): ResultWrapper<ResultType>

    suspend fun <ResultType, RequestType> get(
        key: String,
        retryPolicy: RetryPolicy = RetryPolicy(),
        memoryPolicy: MemoryPolicy = MemoryPolicy(),
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType
    ): ResultWrapper<ResultType>

    suspend fun <ResultType> fresh(
        retryPolicy: RetryPolicy = RetryPolicy(),
        remoteFetch: suspend () -> Response<ResultType>): ResultWrapper<ResultType>

    suspend fun <ResultType, RequestType> fresh(
        retryPolicy: RetryPolicy = RetryPolicy(),
        remoteFetch: suspend () -> Response<RequestType>,
        mapping: (RequestType) -> ResultType
    ): ResultWrapper<ResultType>

    fun getError(throwable: Throwable): ErrorEntity

}