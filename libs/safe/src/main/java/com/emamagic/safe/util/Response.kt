package com.emamagic.safe.util

interface Response<T> {

    fun isSuccessful(): Boolean

    fun body(): T?

    fun code(): Int

    fun errorBody(): String?

}