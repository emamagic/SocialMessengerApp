package com.emamagic.domain.entities

import com.google.gson.annotations.SerializedName

data class ServerConfig(val config: Config, val organization: Organization? = null) {
    fun getServerHost() = "${config.server.protocol}://${config.server.host}"
}
data class Config(
    @SerializedName("fileServerUrl")
    val fileServerUrl: String,
    val videoCallType: String,
    val deploymnetType: String,
    @SerializedName("app.minCompatibleVersion")
    val compatibleVersion: String? = null,
    val showPublicPanelOnInitialLoading: Boolean? = false,
    val authType: String,
    val authServices: String,
    val defaultAuthServices: String,
    val keycloakConfig: KeycloakConfig,
    val server: Server
)
data class KeycloakConfig(
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
data class Server(
    val protocol: String,
    val host: String
)