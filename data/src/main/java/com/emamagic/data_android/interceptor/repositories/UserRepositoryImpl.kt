package com.emamagic.data_android.interceptor.repositories

import android.util.Log
import com.emamagic.cache.cache.data_store.setUser
import com.emamagic.core.AuthUserScope
import com.emamagic.core.ResultWrapper
import com.emamagic.data_android.interceptor.Const
import com.emamagic.data_android.interceptor.network.RestProvider
import com.emamagic.data_android.interceptor.network.auth.UserAuthSession
import com.emamagic.domain.publisher.Event
import com.emamagic.domain.publisher.NotificationCenter
import com.emamagic.domain.entities.PhoneNumber
import com.emamagic.domain.entities.ServerConfig
import com.emamagic.domain.entities.User
import com.emamagic.domain.entities.Workspace
import com.emamagic.data_android.interceptor.toResponse
import com.emamagic.data_android.interceptor.toResult
import com.emamagic.domain.repositories.UserRepository
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import javax.inject.Inject

@AuthUserScope
class UserRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    private val authSession: UserAuthSession
) : SafeApi(), UserRepository, NotificationCenter.NotificationCenterDelegate {

    init {
        NotificationCenter.subscribe(this, Const.LOGGED_OUT)
    }

    override suspend fun getServerUpdate(): ResultWrapper<ServerConfig> = fresh {
            restProvider.configService.getServerConfig().toResponse()
    }.toResult(onSuccess = {
        val config = it.config
        val server = config.server
        restProvider.setBaseUrlAndApiUrl("${server.protocol}://${server.host}")
        restProvider.setBaseFileServerUrl(config.fileServerUrl)
    })

    override suspend fun phoneNumberRegistration(phoneNumber: PhoneNumber): ResultWrapper<Boolean> = fresh {
            restProvider.coordinatorUserService.phoneRegistration(phoneNumber).toResponse()
    }.toResult(shouldReturn = true)

    override suspend fun otpVerification(
        code: String,
        phoneNumber: String,
        deviceId: String
    ): ResultWrapper<User> = fresh {
        restProvider.coordinatorUserService.otpVerification(phoneNumber, code, deviceId).toResponse()
    }.toResult()

    override suspend fun getCurrentUser(shouldFetch: Boolean): ResultWrapper<User> = get("CurrentUser",
            memoryPolicy = MemoryPolicy(shouldRefresh = { shouldFetch })
        ) {
        restProvider.coordinatorUserService.getCurrentUser().toResponse()
        }.toResult(onSuccess = { user -> setUser(user) })

    override suspend fun getMyWorkspaces(shouldFetch: Boolean): ResultWrapper<List<Workspace>> = get("MyWorkspaces",
        memoryPolicy = MemoryPolicy(shouldRefresh = { shouldFetch })) {
        restProvider.coordinatorUserService.getMyWorkspaces().toResponse()
    }.toResult()

    override fun receiveData(event: Event) {
        when (event.id) {
            Const.LOGGED_OUT -> Log.e("TAG", "receiveData: " )
        }
    }

}