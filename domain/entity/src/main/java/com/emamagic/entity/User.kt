package com.emamagic.entity

data class User(
    val avatarHash: String,
    val createAt: Long,
    val deleteAt: Long,
    val email: String,
    val emailVerified: Boolean,
    val firstName: String,
    val isBot: Boolean,
    val lastAvatarUpdateTime: Long,
    val lastName: String,
    val locale: String,
    val nickname: String,
    val notifyProps: NotifyProps,
    val phoneNumber: String,
    val position: String,
    val props: Props,
    val roles: List<String>,
    val uid: String,
    val updateAt: Long,
    val username: String
)

class Props

