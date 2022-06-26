package com.emamagic.domain.entities

data class User(
    val id: String,
    val avatarHash: String? = null,
    val createAt: Long? = null,
    val deleteAt: Long? = null,
    val email: String? = null,
    val emailVerified: Boolean? = null,
    val firstName: String? = null,
    val isBot: Boolean? = null,
    val lastAvatarUpdateTime: Long? = null,
    val lastName: String? = null,
    val locale: String? = null,
    val nickname: String? = null,
    val notifyProps: NotifyProps? = null,
    val phoneNumber: String? = null,
    val position: String? = null,
    val props: Props? = null,
    val roles: List<String>? = null,
    val uid: String? = null,
    val updateAt: Long? = null,
    val username: String? = null
)

class Props

