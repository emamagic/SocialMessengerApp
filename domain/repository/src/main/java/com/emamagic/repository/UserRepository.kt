package com.emamagic.repository

import com.emamagic.common_jvm.ServerConfigResult

interface UserRepository {

    suspend fun getServerUpdate(hostName: String): ServerConfigResult

}