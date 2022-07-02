package com.emamagic.data

import com.emamagic.safe.util.IResponse

class IResponseImpl<T>: IResponse<T> {

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