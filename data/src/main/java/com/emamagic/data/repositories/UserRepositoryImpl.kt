package com.emamagic.data.repositories

import android.util.Log
import com.emamagic.cache.cache.*
import com.emamagic.core.*
import com.emamagic.data.Const
import com.emamagic.data.db.dao.OrganizationDao
import com.emamagic.data.db.dao.WorkspaceDao
import com.emamagic.data.network.RestProvider
import com.emamagic.data.network.auth.UserAuthSession
import com.emamagic.data.toError
import com.emamagic.data.toResponse
import com.emamagic.data.toResult
import com.emamagic.domain.entities.*
import com.emamagic.domain.interactors.*
import com.emamagic.domain.publisher.Event
import com.emamagic.domain.publisher.NotificationCenter
import com.emamagic.domain.repositories.UserRepository
import com.emamagic.safe.SafeApi
import com.emamagic.safe.policy.MemoryPolicy
import javax.inject.Inject
// todo implement logger for logging stuff
// todo implement status like Complatable in Rxjava
@AuthUserScope
class UserRepositoryImpl @Inject constructor(
    private val restProvider: RestProvider,
    private val authSession: UserAuthSession,
    private val workspaceDao: WorkspaceDao,
    private val organizationDao: OrganizationDao,
) : SafeApi(), UserRepository, NotificationCenter.NotificationCenterDelegate {

    init {
        NotificationCenter.subscribe(this, Const.LOGGED_OUT)
    }
//
//    val workspaceStore = StoreBuilder.from(
//        fetcher = Fetcher.of {
//            restProvider.userService.getMyWorkspaces()
//        },
//        sourceOfTruth = SourceOfTruth.of(
//            reader = { workspaceDao.getWorkspaces() },
//            writer = { key, workspaces -> workspaceDao.insert(workspaces) },
//            delete = workspaceDao::delete,
//            deleteAll = workspaceDao::deleteAll
//        )
//    ).build()
//
//    val workspaceResource = ResourceGroup<Unit, String, Workspace>(
//        remoteGroupFetch = { restProvider.userService.getMyWorkspaces() },
//        localGroupFetch = { workspaceDao.getWorkspaces() },
//        localGroupStore = workspaceDao::insert,
//        remoteFetch = { workspaceId, _ -> restProvider.userService.getWorkspaceById(workspaceId) },
//        localFetch = { workspaceId, _ -> workspaceDao.getWorkspaceById(workspaceId) },
//        localStore = workspaceDao::insert,
//        localDelete = workspaceDao::deleteAll
//    )


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

    override suspend fun loginViaPhoneNumber(phoneNumber: LoginViaPhoneNumber.Params): ResultWrapper<Boolean> =
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

    override suspend fun getMyOrganizationWithWorkspaces(): ResultWrapper<OrganizationWithWorkspaces> {
        return try {
            val entity = organizationDao.getOrganizationWithWorkspaces()
            Log.e("TAG", "getMyOrganizationWithWorkspaces: ${entity}", )
            ResultWrapper.Success(entity)
        } catch (t: Throwable) {
            Log.e("TAG", "getMyOrganizationWithWorkspaces: ${t.stackTraceToString()}", )
            ResultWrapper.Failed(getError(t).toError())
        }
    }

    override suspend fun getMyWorkspaces(): ResultWrapper<List<WorkspaceEntity>> = fresh {
        restProvider.userService.getMyWorkspaces().toResponse()
    }.toResult(doOnSuccess = workspaceDao::insert, tryIfFailed = { workspaceDao.getWorkspaces() })

    override suspend fun getMyOrganizations(): ResultWrapper<List<OrganizationEntity>> = fresh {
        restProvider.userService.getMyOrganizations().toResponse()
    }.toResult(doOnSuccess = organizationDao::insert, tryIfFailed = { organizationDao.getOrganizations() })

    override suspend fun loginViaUserName(username: LoginViaUsername.Params): ResultWrapper<Boolean> =
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