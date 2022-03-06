package com.emamagic.common_jvm

import com.emamagic.entity.ServerConfig

sealed class ServerConfigResult : Result<ServerConfigResult.Success> {
    data class Success(val data: ServerConfig) : ServerConfigResult()
    object Loading : ServerConfigResult()
    data class Error(val error: String) : ServerConfigResult()
}
