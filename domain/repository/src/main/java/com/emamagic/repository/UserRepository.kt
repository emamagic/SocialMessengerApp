package com.emamagic.repository

import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.entity.PhoneNumber
import com.emamagic.entity.ServerConfig
import com.emamagic.entity.Status

interface UserRepository {

    suspend fun getServerUpdate(hostName: String): ResultWrapper<ServerConfig>

    suspend fun submitPhoneNumber(phoneNumber: PhoneNumber): ResultWrapper<Status>
}