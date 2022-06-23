package com.emamagic.login

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.emamagic.base.base.BaseViewModel
import com.emamagic.core.PrefKeys
import com.emamagic.core.exhaustive
import com.emamagic.core_android.isValidPhoneNumber
import com.emamagic.domain.interactors.*
import com.emamagic.mvi.BaseEffect
import com.emamagic.login.contract.LoginEvent
import com.emamagic.login.contract.LoginState
import com.emamagic.login.phone.LoginWithPhoneFragmentDirections
import com.emamagic.login.user_name.LoginWithUsernameFragmentDirections
import com.emamagic.mvi.LoginEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import net.openid.appauth.ResponseTypeValues
import javax.inject.Inject

@Suppress("IMPLICIT_CAST_TO_ANY")
@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val updateServerConfig: UpdateServerConfig,
    private val verifyOtp: VerifyOtp,
    private val loginWithPhoneNumber: LoginWithPhoneNumber,
    private val loginWithUsername: LoginWithUsername,
    private val saveToCache: SaveToCache,
    private val loginWithKeycloak: LoginWithKeycloak,
) : BaseViewModel<LoginState, LoginEvent>() {

    private lateinit var G_phoneNumber: String

    init {
        getServerConfigEvent()
    }

    override fun createInitialState() = LoginState.initialize()

    override fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.TypingPhoneNumberEvent -> typingPhoneNumberEvent(event.phoneNumber)
            LoginEvent.ChangeServerNameClickEvent -> changeServerNameClickedEvent()
            LoginEvent.LoginWithUsernameClickEvent -> loginWithUsernameClickedEvent()
            is LoginEvent.SubmitPhoneNumberEvent -> submitPhoneNumberEvent(event.phoneNumber, event.countryCode)
            LoginEvent.SubmitTermsPolicyEvent -> submitTermsPolicyEvent()
            is LoginEvent.SubmitUserNameEvent -> submitUserNameEvent(event.username, event.pass)
            is LoginEvent.TypingUserNameOrPassEvent -> typingUserNameOrPassEvent(event.username, event.pass)
            LoginEvent.LoginWithPhoneNumberClickEvent -> loginWithPhoneClickedEvent()
            is LoginEvent.ChangeServerNameEvent -> changeServerNameClickedEvent(event.host)
            LoginEvent.LoginWithKeycloakEvent -> loginWithKeycloakEvent()
            is LoginEvent.SubmitOtpEvent -> submitOtpEvent(event.code)
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
        navigateTo(LoginWithPhoneFragmentDirections.actionLoginWithPhoneFragmentToChangeServerNameFragment())
    }

    private fun loginWithUsernameClickedEvent() = viewModelScope.launch {
        navigateTo(LoginWithPhoneFragmentDirections.actionLoginWithPhoneFragmentToLoginWithUsernameFragment())
    }

    private fun submitPhoneNumberEvent(phoneNum: String, countryCode: String) = withLoadingScope {
        if (!phoneNum.isValidPhoneNumber()) {
            setEffect { BaseEffect.InvalidInput.Error() }
            return@withLoadingScope
        }
        G_phoneNumber = if (phoneNum.length == 11) countryCode + phoneNum.substring(1)
        else countryCode + phoneNum
        loginWithPhoneNumber(LoginWithPhoneNumber.Params(G_phoneNumber)).manageResult {
            navigateTo(LoginWithPhoneFragmentDirections.actionLoginWithPhoneFragmentToOtpFragment())
        }
    }

    private fun getServerConfigEvent() = withLoadingScope {
        updateServerConfig("https://test.limonadapp.ir").manageResult()
    }

    private fun submitTermsPolicyEvent() = withLoadingScope {
        navigateTo(
            LoginWithPhoneFragmentDirections
                .actionLoginWithPhoneFragmentToTermsPolicyFragment(R.string.privacy_policy_description)
        )
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
        loginWithUsername(LoginWithUsername.Params(username, pass)).manageResult {
            navigateTo(LoginWithUsernameFragmentDirections.actionOtpFragmentToConversationFragment())
        }
    }

    private fun typingUserNameOrPassEvent(userName: String, pass: String) = withLoadingScope {
        if (userName.trim().isNotEmpty() && pass.trim().isNotEmpty()) {
            setEffect { BaseEffect.EnableUiComponent() }
        } else {
            setEffect { BaseEffect.DisableUiComponent() }
        }
    }

    private fun loginWithPhoneClickedEvent() = withLoadingScope {
        setEffect { BaseEffect.NavigateBack }
    }


    // ---------------- singin with server name ----------------

    private fun changeServerNameClickedEvent(host: String) = viewModelScope.launch {
        if (host.trim().isEmpty() || host.trim().contains(" ")) {
            setEffect { BaseEffect.InvalidInput.Error() }
            return@launch
        }
        setEffect { BaseEffect.ShowLoading() }
        updateServerConfig(host).manageResult { serverConfig ->
            val authTypes = serverConfig.config.authServices
                .replace("[", "")
                .replace("]", "")
                .split(",")
            setState {
                copy(
                    serverConfigUpdated = serverConfig.getServerHost(),
                    authTypes = authTypes
                )
            }
            if (serverConfig.config.authType.equals("keycloak", true)) {
                val keycloak = serverConfig.config.keycloakConfig
                val authorizationEndpoint =
                    keycloak.authServerUrl + "realms/" + keycloak.realm + "/protocol/openid-connect/auth"
                val tokenEndpoint =
                    keycloak.authServerUrl + "realms/" + keycloak.realm + "/protocol/openid-connect/token"
                val resource = keycloak.resource
                val redirectUri = context.getString(R.string.keycloak_redirect_uri)
                val scope = context.getString(R.string.keycloak_scope)
                val responseType = ResponseTypeValues.CODE
                setEffect {
                    LoginEffect.Keycloak(
                        authorizationEndpoint,
                        tokenEndpoint,
                        redirectUri,
                        resource,
                        scope,
                        responseType
                    )
                }
                // setState for LoginWithPhoneFragment
            } else {
                setEffect { BaseEffect.NavigateBack }
            }
        }
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
        saveToCache(SaveToCache.Params(PrefKeys.CertAlias, alias)).manageResult { succeeded ->
            if (!succeeded) setEffect { BaseEffect.Toast("could not save alias") }
            setEffect { LoginEffect.PerformAuthorization(authorizationEndpoint,tokenEndpoint, redirectUri, clientId, scope, responseType) }
        }
    }

    private fun loginWithKeycloakEvent() = viewModelScope.launch {
        loginWithKeycloak(Unit).manageResult {
            Log.e("TAG", "loginWithKeycloakEvent: successfully")
        }
    }

    // ---------------- otp ----------------

    private fun otpExpired() = withLoadingScope {

    }

    private fun submitOtpEvent(code: String) = withLoadingScope {
        if (code.length != 5) {
            setEffect { BaseEffect.InvalidInput.Error() }
            return@withLoadingScope
        }
        @SuppressLint("HardwareIds")
        val deviceId = Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID
        )
        verifyOtp(VerifyOtp.Params(code, G_phoneNumber, deviceId)).manageResult {
            navigateTo(LoginWithUsernameFragmentDirections.actionOtpFragmentToConversationFragment())
        }
    }

}