package com.emamagic.entity

data class Error(
    val id: String? = null,
    val message: String? = null,
    val requestId: String? = null,
    val statusCode: Int? = null,
    val isOauth: Boolean? = null,
    val i18n_key: String? = null,
    val display_message: String? = null,
    // these below value set on client side
    val throwable: Throwable? = null,
    val errorType: String? = null
)