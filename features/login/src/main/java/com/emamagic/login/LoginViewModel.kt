package com.emamagic.login

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.emamagic.common_ui.base.BaseViewModel
import com.emamagic.core.*
import com.emamagic.core_android.ToastScope
import com.emamagic.core_android.isValidPhoneNumber
import com.emamagic.domain.entities.ServerConfig
import com.emamagic.domain.interactors.*
import com.emamagic.login.contract.LoginEvent
import com.emamagic.login.contract.LoginRouter
import com.emamagic.login.contract.LoginState
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.LoginEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import javax.inject.Inject

@Suppress("IMPLICIT_CAST_TO_ANY")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getServerConfig: GetServerConfig,
    private val verifyOtp: VerifyOtp,
    private val loginViaPhoneNumber: LoginViaPhoneNumber,
    private val loginViaUsername: LoginViaUsername,
    private val saveAlias: SaveAlias,
    private val loginViaKeycloak: LoginViaKeycloak,
    private val getCurrentUser: GetCurrentUser,
    private val checkLoginProcess: CheckLoginProcess
) : BaseViewModel<LoginState, LoginEvent, LoginRouter.Routes>() {

    private lateinit var G_phoneNumber: String

    override fun createInitialState() = LoginState.initialize()

    private fun init() {
        setEffect { BaseEffect.ShowLoading(scope = ToastScope.MODULE_SCOPE) }
        withLoadingScope {
            when (val result = getCurrentUser(Unit)) {
                is ResultWrapper.FetchLoading -> TODO()
                is ResultWrapper.Failed -> checkIfLoginNeeded(result.error!!)
                is ResultWrapper.Success -> {
                    when (checkLoginProcess()) {
                        CheckLoginProcess.LoginProcessResult.GoToConversation -> routerDelegate.pushRoute(LoginRouter.Routes.ToConversations)
                        CheckLoginProcess.LoginProcessResult.GoToSignup -> routerDelegate.pushRoute(LoginRouter.Routes.ToSignup)
                        CheckLoginProcess.LoginProcessResult.GoToWorkspaceCreate -> routerDelegate.pushRoute(LoginRouter.Routes.ToWorkspaceCreate)
                        CheckLoginProcess.LoginProcessResult.GoToWorkspaceSelect -> routerDelegate.pushRoute(LoginRouter.Routes.ToWorkspaceSelect)
                    }
                }
            }
        }
    }

    private suspend fun checkIfLoginNeeded(error: Error) {
        getServerConfig {
            when (error.statusCode) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    if (it.config.isLoginViaPhoneNumberAllowed) { // do nothing
                    } else if (it.config.isLoginViaUsernameAllowed) {
                        routerDelegate.pushRoute(LoginRouter.Routes.LoginViaPhoneNumberToLoginViaUsername)
                    } else {
                        setEffect { BaseEffect.Toast("you are not allowed to login") }
                        setState { copy(closeApp = true) }
                    }
                }
                LimooHttpCode.HTTP_SIGNUP -> {
                    routerDelegate.pushRoute(LoginRouter.Routes.ToSignup)
                } else -> {
                    // TODO() unknown status
                }
            }
            setEffect { BaseEffect.HideLoading(scope = ToastScope.MODULE_SCOPE) }
        }
    }

    private suspend fun checkForWorkspaceLink() {
        // todo workspace-link
    }

    private suspend fun getServerConfig(call: (ServerConfig) -> Unit) {
        val result = getServerConfig(GetServerConfig.Params(null, true))
        if (result.succeeded) {
            call.invoke(result.data!!)
        } else { // no network or ...
            setEffect { BaseEffect.Toast("check your connectivity dude") }
            setState { copy(closeApp = true) }
        }
    }

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.InitEvent -> init()
            is LoginEvent.TypingPhoneNumberEvent -> typingPhoneNumberEvent(event.phoneNumber)
            LoginEvent.ChangeServerNameClickEvent -> changeServerNameClickedEvent()
            LoginEvent.LoginWithUsernameClickEvent -> loginWithUsernameClickedEvent()
            is LoginEvent.SubmitPhoneNumberEvent -> submitPhoneNumberEvent(
                event.phoneNumber,
                event.countryCode
            )
            LoginEvent.SubmitTermsPolicyEvent -> submitTermsPolicyEvent()
            is LoginEvent.SubmitUserNameEvent -> submitUserNameEvent(event.username, event.pass)
            is LoginEvent.TypingUserNameOrPassEvent -> typingUserNameOrPassEvent(
                event.username,
                event.pass
            )
            LoginEvent.LoginWithPhoneNumberClickEvent -> loginWithPhoneClickedEvent()
            is LoginEvent.ChangeServerNameEvent -> changeServerNameClickedEvent(event.host)
            LoginEvent.LoginWithKeycloakEvent -> loginWithKeycloakEvent()
            is LoginEvent.SubmitOtpEvent -> submitOtpEvent(event.code, event.deviceId)
            LoginEvent.OtpExpiredEvent -> otpExpired()
        }.exhaustive
    }


    // ---------------- singin with phone ----------------

    private fun typingPhoneNumberEvent(input: String) = withLoadingScope {
        if ((input.length == 10 && input.first() != '0') || (input.length == 11 && input.first() == '0')) {
            setEffect { BaseEffect.HideKeyboard }
            setEffect { BaseEffect.EnableUiComponent() }
        } else if (input.length == 9 || (input.length == 11 && input.first() != '0')) {
            setEffect { BaseEffect.DisableUiComponent() }
        }
    }

    private fun changeServerNameClickedEvent() = viewModelScope.launch {
        routerDelegate.pushRoute(LoginRouter.Routes.LoginViaPhoneNumberToChangeServer)
    }

    private fun loginWithUsernameClickedEvent() = viewModelScope.launch {
        routerDelegate.pushRoute(LoginRouter.Routes.LoginViaPhoneNumberToLoginViaUsername)
    }

    private fun submitPhoneNumberEvent(phoneNum: String, countryCode: String) = withLoadingScope {
        if (!phoneNum.isValidPhoneNumber()) {
            setEffect { BaseEffect.InvalidInput.Error() }
            return@withLoadingScope
        }
        G_phoneNumber = if (phoneNum.length == 11) countryCode + phoneNum.substring(1)
        else countryCode + phoneNum
        loginViaPhoneNumber(LoginViaPhoneNumber.Params(G_phoneNumber)).manageResult(success = {
            routerDelegate.pushRoute(LoginRouter.Routes.LoginViaPhoneNumberToOtp)
        })
    }

    private fun submitTermsPolicyEvent() = withLoadingScope {
//        navigateTo(
//            LoginWithPhoneFragmentDirections
//                .actionLoginWithPhoneFragmentToTermsPolicyFragment(R.string.privacy_policy_description)
//        )
    }

    // ---------------- singin with username ----------------

    /**
     * BaseEffect.EmptyInputValue ->
     * true -> username is empty
     * false -> pass is empty
     **/
    private fun submitUserNameEvent(username: String, pass: String) = withLoadingScope {
        if (username.isEmpty()) {
            setEffect { BaseEffect.InvalidInput.Error(type = true) }
            return@withLoadingScope
        }
        if (pass.isEmpty()) {
            setEffect { BaseEffect.InvalidInput.Error(type = false) }
            return@withLoadingScope
        }
        loginViaUsername(LoginViaUsername.Params(username, pass)).manageResult ( success = { init() })
    }

    private fun typingUserNameOrPassEvent(userName: String, pass: String) = withLoadingScope {
        if (userName.trim().isNotEmpty() && pass.trim().isNotEmpty()) {
            setEffect { BaseEffect.EnableUiComponent() }
        } else {
            setEffect { BaseEffect.DisableUiComponent() }
        }
    }

    private fun loginWithPhoneClickedEvent() = withLoadingScope {
//        setEffect { BaseEffect.NavigateBack }
    }


    // ---------------- singin with server name ----------------

    private fun changeServerNameClickedEvent(host: String) = viewModelScope.launch {
        if (host.trim().isEmpty() || host.trim().contains(" ")) {
            setEffect { BaseEffect.InvalidInput.Error() }
            return@launch
        }
        setEffect { BaseEffect.ShowLoading() }
        getServerConfig(
            GetServerConfig.Params(
                host,
                true
            )
        ).manageResult(success = { serverConfig ->
            serverConfig?.let {
                setState {
                    copy(
                        defaultAuthType = serverConfig.config.defaultAuthServices,
                        deploymentType = serverConfig.config.deploymentType
                    )
                }
            }
//            val authTypes = serverConfig.config.authServices
//                .replace("[", "")
//                .replace("]", "")
//                .split(",")
//            setState {
//                copy(
//                    serverConfigUpdated = serverConfig.getServerHost(),
//                    authTypes = authTypes
//                )
//            }
//            if (serverConfig.config.authType.equals("keycloak", true)) {
//                val keycloak = serverConfig.config.keycloakConfig
//                val authorizationEndpoint =
//                    keycloak.authServerUrl + "realms/" + keycloak.realm + "/protocol/openid-connect/auth"
//                val tokenEndpoint =
//                    keycloak.authServerUrl + "realms/" + keycloak.realm + "/protocol/openid-connect/token"
//                val resource = keycloak.resource
//                val redirectUri = context.getString(R.string.keycloak_redirect_uri)
//                val scope = context.getString(R.string.keycloak_scope)
//                val responseType = ResponseTypeValues.CODE
//                setEffect {
//                    LoginEffect.Keycloak(
//                        authorizationEndpoint,
//                        tokenEndpoint,
//                        redirectUri,
//                        resource,
//                        scope,
//                        responseType
//                    )
//                }
//                 setState for LoginWithPhoneFragment
//            } else {
//                setEffect { BaseEffect.NavigateBack }
//            }
        })
    }

    fun processSSlCertResult(
        alias: String?,
        authorizationEndpoint: String,
        tokenEndpoint: String,
        redirectUri: String,
        clientId: String,
        scope: String,
        responseType: String
    ) = viewModelScope.launch {
        if (!alias.isNullOrEmpty()) {
            saveAlias(SaveAlias.Params(alias)).manageResult()
        }
        setEffect {
            LoginEffect.PerformAuthorization(
                authorizationEndpoint,
                tokenEndpoint,
                redirectUri,
                clientId,
                scope,
                responseType
            )
        }
    }

    private fun loginWithKeycloakEvent() = viewModelScope.launch {
        loginViaKeycloak(Unit).manageResult {
            Log.e("TAG", "loginWithKeycloakEvent: successfully")
        }
    }

    // ---------------- otp ----------------

    private fun otpExpired() = withLoadingScope {

    }

    private fun submitOtpEvent(code: String, deviceId: String) = withLoadingScope {
        if (code.length != 5) {
            setEffect { BaseEffect.InvalidInput.Error() }
            return@withLoadingScope
        }
        val result = verifyOtp(VerifyOtp.Params(code, G_phoneNumber, deviceId))
        when (result) {
            is ResultWrapper.FetchLoading -> TODO()
            is ResultWrapper.Failed ->  setEffect { BaseEffect.InvalidInput.Error("invalid otp code") } // invalid otp code or ...
            is ResultWrapper.Success -> init()
        }.exhaustive
    }


    // ---------------- signup ----------------


    // ---------------- intro ----------------

}