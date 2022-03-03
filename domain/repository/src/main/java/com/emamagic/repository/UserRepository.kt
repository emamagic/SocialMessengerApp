package com.emamagic.repository

import com.emamagic.common_jvm.ResultWrapper

interface UserRepository {

    suspend fun getServerUpdate(hostName: String): ResultWrapper<*>

}