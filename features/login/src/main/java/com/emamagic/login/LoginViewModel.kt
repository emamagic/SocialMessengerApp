package com.emamagic.login

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.viewModelScope
import com.emamagic.base.base.BaseViewModel
import com.emamagic.core_android.isValidPhoneNumber
import com.emamagic.domain.interactors.VerifyOtp
import com.emamagic.domain.interactors.LoginWithPhoneNumber
import com.emamagic.domain.interactors.LoginWithUsername
import com.emamagic.domain.interactors.UpdateServerConfig
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.LoginEffect
import com.emamagic.login.contract.LoginAction
import com.emamagic.login.contract.LoginState
import com.emamagic.login.phone.LoginWithPhoneFragmentDirections
import com.emamagic.login.user_name.LoginWithUsernameFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val updateServerConfig: UpdateServerConfig,
    private val verifyOtp: VerifyOtp,
    private val loginWithPhoneNumber: LoginWithPhoneNumber,
    private val loginWithUsername: LoginWithUsername,
): BaseViewModel<LoginState, LoginAction>() {

    private lateinit var G_phoneNumber: String

    init {
        getServerConfigEvent()
    }

    // ---------------- singin with phone ----------------

    fun typingPhoneNumberEvent(input: String) = withLoadingScope {
            if ((input.length == 10 && input.first() != '0') || (input.length == 11 && input.first() == '0')) {
                setEffect { BaseEffect.HideKeyboard }
                setEffect { BaseEffect.EnableUiComponent() }
            } else if (input.length == 9 || (input.length == 11 && input.first() != '0')){
                setEffect { BaseEffect.DisableUiComponent() }
            }
    }

    fun changeServerNameClickedEvent() = viewModelScope.launch {
        navigateTo(LoginWithPhoneFragmentDirections.actionLoginWithPhoneFragmentToChangeServerNameFragment())
    }

    fun loginWithUsernameClickedEvent() = viewModelScope.launch {
        navigateTo(LoginWithPhoneFragmentDirections.actionLoginWithPhoneFragmentToLoginWithUsernameFragment())
    }

    fun submitPhoneNumberEvent(phoneNum: String, countryCode: String) = withLoadingScope {
        if (!phoneNum.isValidPhoneNumber()) {
            setEffect { BaseEffect.InvalidInputValue() }
            return@withLoadingScope
        }
        G_phoneNumber = if (phoneNum.length == 11) countryCode + phoneNum.substring(1)
        else countryCode + phoneNum
        loginWithPhoneNumber(LoginWithPhoneNumber.Params(G_phoneNumber)).manageResult {
            navigateTo(LoginWithPhoneFragmentDirections.actionLoginWithPhoneFragmentToOtpFragment())
        }
    }

    private fun getServerConfigEvent() = withLoadingScope {
        updateServerConfig(Unit).manageResult()
    }

    fun submitTermsPolicyEvent() = withLoadingScope {
        navigateTo(LoginWithPhoneFragmentDirections
            .actionLoginWithPhoneFragmentToTermsPolicyFragment(R.string.privacy_policy_description))
    }

    // ---------------- singin with username ----------------

    /**
     * BaseEffect.EmptyInputValue ->
     * true -> username is empty
     * false -> pass is empty
     * */
    fun submitUserNameEvent(username: String, pass: String) = withLoadingScope {
        if (username.isEmpty()) {
            setEffect { BaseEffect.EmptyInputValue(true) }
            return@withLoadingScope
        }
        if (pass.isEmpty()) {
            setEffect { BaseEffect.EmptyInputValue(false) }
            return@withLoadingScope
        }
        loginWithUsername(LoginWithUsername.Params(username, pass)).manageResult {
            navigateTo(LoginWithUsernameFragmentDirections.actionOtpFragmentToConversationFragment())
        }
    }

    fun typingUserNameOrPassEvent(userName: String, pass: String) = withLoadingScope {
        if (userName.trim().isNotEmpty() && pass.trim().isNotEmpty()) {
            setEffect { BaseEffect.EnableUiComponent() }
        } else {
            setEffect { BaseEffect.DisableUiComponent() }
        }
    }

    fun loginWithPhoneClickedEvent() = withLoadingScope {
        setEffect { BaseEffect.NavigateBack }
    }


    // ---------------- singin with server name ----------------

    fun changeServerNameClickedEvent(host: String) = viewModelScope.launch {
        if (host.trim().isEmpty() || host.trim().contains(" ")) {
            setEffect { BaseEffect.InvalidInputValue() }
        }
    }


    // ---------------- otp ----------------

    fun otpExpired() = withLoadingScope {

    }

    fun submitOtpEvent(code: String) = withLoadingScope {
        if (code.length != 5) {
            setEffect { BaseEffect.InvalidInputValue() }
            return@withLoadingScope
        }
        @SuppressLint("HardwareIds")
        val deviceId = Settings.Secure.getString(context.applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        verifyOtp(VerifyOtp.Params(code, G_phoneNumber, deviceId)).manageResult {
            navigateTo(LoginWithUsernameFragmentDirections.actionOtpFragmentToConversationFragment())
        }
    }

    override fun createInitialState() = LoginState.initialize()

    override fun handleEvent(event: LoginAction) {
        
    }

}