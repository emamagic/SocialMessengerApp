package com.franmontiel.persistentcookiejar.token

interface TokenAccessor {

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

}