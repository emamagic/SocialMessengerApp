package com.emamagic.data_android.interceptor.network.services

import com.emamagic.domain.entities.ServerConfig
import retrofit2.Response
import retrofit2.http.GET

interface ConfigService {

    @GET("config")
    suspend fun getServerConfig(): Response<ServerConfig>

}