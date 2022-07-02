package com.emamagic.data.network.auth


sealed class UserAuth {
    data class Authenticated(
        val id: String,
        val credential: String
    ): UserAuth()

    object Unauthenticated: UserAuth()
}
