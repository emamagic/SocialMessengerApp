package com.emamagic.data.network.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserAuthSession @Inject constructor() {

    private val _currentAuth = MutableStateFlow<UserAuth>(UserAuth.Unauthenticated)
    val currentAuth = _currentAuth.asStateFlow()

    fun login(userId: String, avatarHash: String?) {
        _currentAuth.tryEmit(
            UserAuth.Authenticated(
                id = userId,
                avatarHash = avatarHash
            )
        )
    }
    fun logout() {
        _currentAuth.tryEmit(UserAuth.Unauthenticated)
    }

    fun isLoggedIn(): Boolean {
        return currentAuth.value is UserAuth.Authenticated
    }
}