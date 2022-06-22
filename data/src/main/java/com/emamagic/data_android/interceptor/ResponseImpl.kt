package com.emamagic.data_android.interceptor

import com.emamagic.safe.util.Response

class ResponseImpl<T>: Response<T> {

    override fun isSuccessful(): Boolean {
        TODO("Not yet implemented")
    }

    override fun body(): T? {
        TODO("Not yet implemented")
    }

    override fun code(): Int {
        TODO("Not yet implemented")
    }

    override fun errorBody(): String? {
        TODO("Not yet implemented")
    }
}