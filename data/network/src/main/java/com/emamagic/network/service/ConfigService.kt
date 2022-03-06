package com.emamagic.network.service

import com.emamagic.entity.ServerConfig
import retrofit2.Response
import retrofit2.http.GET

interface ConfigService {

    @GET("config")
    suspend fun getServerConfig(): Response<ServerConfig>

}