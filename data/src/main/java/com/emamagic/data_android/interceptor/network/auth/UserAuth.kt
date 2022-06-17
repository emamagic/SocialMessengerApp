package com.emamagic.data_android.interceptor.network.auth


sealed class UserAuth {
    data class Authenticated(
        val id: String,
        val credential: String
    ): UserAuth()

    object Unauthenticated: UserAuth()
}
