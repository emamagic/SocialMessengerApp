package com.emamagic.entity

data class Error(
    val id: String?,
    val message: String?,
    val requestId: String?,
    val statusCode: Int?,
    val isOauth: Boolean?,
    val i18n_key: String?,
    val display_message: String?
)