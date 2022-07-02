package com.emamagic.limoo

import com.emamagic.data.network.auth.UserAuth
import com.emamagic.data.network.auth.UserAuthSession
import com.emamagic.limoo.di.AuthUserComponent
import dagger.hilt.internal.GeneratedComponentManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AuthUserComponentManager @Inject constructor(
    private val userAuthSession: UserAuthSession,
    private val userComponentProvider: Provider<AuthUserComponent.Builder>,
    appScope: CoroutineScope
): GeneratedComponentManager<AuthUserComponent> {

    private val _versionState = MutableStateFlow(ComponentVersion.next())
    val versionState = _versionState.asStateFlow()

    var authUserComponent: AuthUserComponent = userComponentProvider.get().build()

    private var lastUserAuth: UserAuth = userAuthSession.currentAuth.value
    init {
        appScope.launch {
            // Observe AuthState.
            userAuthSession.currentAuth.collect { userAuth ->
                if (lastUserAuth == userAuth) return@collect
                // Rebuild AuthUserComponent if current user is changed.
                lastUserAuth = userAuth
                rebuildComponent()
            }
        }
    }

    suspend fun rebuildComponent() {
        authUserComponent = userComponentProvider.get().build()
        _versionState.emit(ComponentVersion.next())
    }

    override fun generatedComponent(): AuthUserComponent = authUserComponent
}

data class ComponentVersion constructor(
    private val version: Int = versionSeq.incrementAndGet()
) {
    companion object {
        private val versionSeq = AtomicInteger(0)

        fun next(): ComponentVersion = ComponentVersion()
    }
}