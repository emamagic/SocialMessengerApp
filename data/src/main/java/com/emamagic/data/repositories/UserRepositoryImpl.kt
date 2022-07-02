package com.emamagic.data.repositories

import android.util.Log
import com.emamagic.cache.cache.*
import com.emamagic.core.AuthUserScope
import com.emamagic.core.PrefKeys
import com.emamagic.core.ResultWrapper
import com.emamagic.data.Const
import com.emamagic.data.db.dao.WorkspaceDao
import com.emamagic.data.network.RestProvider
import com.emamagic.data.network.auth.UserAuthSession
import com.emamagic.data.syncers.networkBoundResource
import com.emamagic.data.toResponse
import com.emamagic.data.toResult
import com.emamagic.domain.entities.ServerConfig
import com.emamagic.domain.entities.User
import com.emamagic.domain.entities.Workspace
import com.emamagic.domain.interactors.*
import com.emamagic.domain.publisher.Event
import com.emamagic.domain.publisher.NotificationCenter
import com.emamagic.domain.repositories.UserRepository
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
// todo implement logger for logging stuff
// todo implement status like Complatable in Rxjava
@AuthUserScope
class UserRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    private val authSession: UserAuthSession,
    private val workspaceDao: WorkspaceDao
) : SafeApi(), UserRepository, NotificationCenter.NotificationCenterDelegate {

    init {
        NotificationCenter.subscribe(this, Const.LOGGED_OUT)
    }

    override suspend fun updateServerConfig(params: UpdateServerConfig.Params): ResultWrapper<ServerConfig> {
        restProvider.setBaseUrlAndApiUrl(params.serverHost)
        return get("serverConfig", memoryPolicy = MemoryPolicy(shouldRefresh = { params.shouldRefresh })) {
            restProvider.configService.getServerConfig().toResponse()
        }.toResult(doOnSuccess = {
            val config = it.config
            restProvider.setBaseUrlAndApiUrl(config.server.getServerHost())
            restProvider.setBaseFileServerUrl(config.fileServerUrl)
        })
    }

    override suspend fun loginWithPhoneNumber(phoneNumber: LoginWithPhoneNumber.Params): ResultWrapper<Boolean> =
        fresh {
            restProvider.userService.loginWithPhoneNumber(phoneNumber).toResponse()
        }.toResult(shouldReturn = true)

    override suspend fun verifyOtp(params: VerifyOtp.Params): ResultWrapper<User> = fresh {
        restProvider.userService.verifyOtp(params.phoneNumber, params.code, params.deviceId)
            .toResponse()
    }.toResult()

    override suspend fun getCurrentUser(): ResultWrapper<User> = get("currentUser") {
        restProvider.userService.getCurrentUser().toResponse()
    }.toResult(doOnSuccess = { pref[PrefKeys.CURRENT_USER] = it }
        ,tryIfFailed = { pref[PrefKeys.CURRENT_USER] })

    override fun getMyWorkspaces(): Flow<ResultWrapper<List<Workspace>>> = networkBoundResource(
        this,
        databaseQuery = { workspaceDao.getAWorkspaces() },
        networkCall = { restProvider.userService.getMyWorkspaces() },
        saveCallResult = { workspaceDao.insert(it) }
    ).catch {
        // todo implement this error
        Log.e("TAG", "getMyWorkspaces: ${it.stackTraceToString()}", )
    }

    override suspend fun loginWithUserName(username: LoginWithUsername.Params): ResultWrapper<Boolean> =
        fresh {
            restProvider.userServiceCoordinator.loginWithUserName(username.username, username.pass)
                .toResponse()
        }.toResult()

    override suspend fun getSessionByKeycloak(): ResultWrapper<Boolean> = fresh {
        restProvider.userService.getSessionByKeycloak().toResponse()
    }.toResult()

    override suspend fun saveAlias(data: SaveAlias.Params): ResultWrapper<Boolean> {
        return try {
            pref[PrefKeys.CertAlias] = data.alias
            ResultWrapper.Success(true)
        } catch (t: Throwable) {
            ResultWrapper.Success(false)
        }
    }

    override fun receiveData(event: Event) {
        when (event.id) {
            Const.LOGGED_OUT -> {
                authSession.logout()
            }
        }
    }

}