package com.emamagic.repository_impl.mapper

import retrofit2.Response

class ResponseMapper<T> constructor(
    private val response: Response<T>
): com.emamagic.safe.util.Response<T> {

    override fun isSuccessful(): Boolean {
        return response.isSuccessful
    }

    override fun body(): T? {
        return response.body()
    }

    override fun code(): Int {
        return response.code()
    }

    override fun errorBody(): String? {
        return response.errorBody()?.string()
    }

}