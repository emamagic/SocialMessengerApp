package com.emamagic.network.dto

import com.google.gson.annotations.SerializedName

data class ServerConfigDto(val config: Config, val organization: Organization? = null)
data class Config(
    val fileServerUrl: String,
    val videoCallType: String,
    val deploymnetType: String,
    @SerializedName("app.minCompatibleVersion")
    val compatibleVersion: String? = null,
    val showPublicPanelOnInitialLoading: Boolean? = false,
    val authType: String,
    val authServices: String,
    val defaultAuthServices: String,
    val keycloakConfig: Keycloak
)
data class Keycloak(
    val realm: String,
    @SerializedName("auth-server-url")
    val authServerUrl: String,
    val sslRequired: String,
    val resource: String,
    val publicClient: String
)
data class Organization(
    val backgroundHash: String? = null,
    val logoHash: String? = null
)