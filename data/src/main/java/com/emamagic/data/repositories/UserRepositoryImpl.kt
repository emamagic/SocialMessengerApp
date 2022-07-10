package com.emamagic.data.repositories

import com.emamagic.cache.cache.*
import com.emamagic.core.*
import com.emamagic.data.*
import com.emamagic.data.db.dao.OrganizationDao
import com.emamagic.data.db.dao.WorkspaceDao
import com.emamagic.data.network.RestProvider
import com.emamagic.data.network.auth.UserAuthSession
import com.emamagic.domain.entities.*
import com.emamagic.domain.interactors.*
import com.emamagic.domain.interactors.login.LoginViaPhoneNumber
import com.emamagic.domain.interactors.login.LoginViaUsername
import com.emamagic.domain.interactors.login.SignupUser
import com.emamagic.domain.interactors.login.VerifyOtp
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

    override suspend fun getServerConfig(params: GetServerConfig.Params): ResultWrapper<ServerConfig> {
        params.serverHost?.let {
            restProvider.setBaseUrlAndApiUrl(it)
            pref[PrefKeys.HOST] = it
        }
        return get(
            "serverConfig",
            memoryPolicy = MemoryPolicy(shouldRefresh = { params.shouldRefresh })
        ) {
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
    }.toResult(doOnSuccess = {
        pref[PrefKeys.CURRENT_USER] = it
        authSession.login(it.id, it.avatarHash)
    }, tryIfFailed = {
        val user: User? = pref[PrefKeys.CURRENT_USER]
        if (user != null) { authSession.login(user.id, user.avatarHash) }
        user
    })


    override suspend fun getMyWorkspaces(): ResultWrapper<List<WorkspaceEntity>> = fresh {
        restProvider.userService.getMyWorkspaces().toResponse()
    }.toResult(doOnSuccess = workspaceDao::insert, tryIfFailed = { workspaceDao.getWorkspaces() })

    override suspend fun getMyOrganizations(): ResultWrapper<List<OrganizationEntity>> = fresh {
        restProvider.userService.getMyOrganizations().toResponse()
    }.toResult(
        doOnSuccess = organizationDao::insert,
        tryIfFailed = { organizationDao.getOrganizations() })

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
            pref[PrefKeys.CERT_AlIAS] = data.alias
            ResultWrapper.Success(true)
        } catch (t: Throwable) {
            ResultWrapper.Success(false)
        }
    }

    override suspend fun isLoggedIn(): Boolean = authSession.isLoggedIn()

    override suspend fun hasIntroBeenSeen(): Boolean = pref[PrefKeys.INTRO_SEEN, false] as Boolean

    override suspend fun userSawIntro() { pref[PrefKeys.INTRO_SEEN] = true }

    override suspend fun signupUser(params: SignupUser.Params): ResultWrapper<User> = fresh {
        restProvider.userService.signup(params).toResponse()
    }.toResult(doOnSuccess = {
        pref[PrefKeys.CURRENT_USER] = it
    })

    override fun receiveData(event: Event) {
        when (event.id) {
            Const.LOGGED_OUT -> {
                authSession.logout()
            }
        }
    }

}