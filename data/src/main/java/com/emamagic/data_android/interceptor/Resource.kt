package com.emamagic.data_android.interceptor

import java.util.*

class Resource<T> constructor(val status: Status, var data: T? = null, val message: String? = null, val date: Date? = null) {

    companion object {
        fun <T> success(data: T?, date: Date?): Resource<T> {
            return Resource(status = Status.SUCCESS, data = data, date = date)
        }

        fun <T> error(msg: String?, data: T?, date: Date?): Resource<T> {
            return Resource(Status.ERROR, data, msg, date)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(status = Status.LOADING, data = data)
        }

        fun <T> cached(data: T?, date: Date?): Resource<T> {
            return Resource(status = Status.CACHED, data = data, date = date)
        }

        fun <T> reAuthenticate(): Resource<T> {
            return Resource(Status.REAUTH)
        }

        fun <T> logout(): Resource<T> {
            return Resource(Status.LOGOUT)
        }
    }

}

enum class Status {
    SUCCESS, ERROR, LOADING, CACHED, REAUTH, LOGOUT
}