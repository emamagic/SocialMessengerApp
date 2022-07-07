package com.emamagic.data.network.auth


sealed class UserAuth {
    data class Authenticated(
        val id: String,
        val avatarHash: String?
    ): UserAuth()

    object Unauthenticated: UserAuth()
}
