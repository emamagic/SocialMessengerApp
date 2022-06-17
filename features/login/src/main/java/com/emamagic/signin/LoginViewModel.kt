package com.emamagic.signin

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import com.emamagic.base.base.BaseViewModel
import com.emamagic.domain.entities.PhoneNumber
import com.emamagic.domain.interactors.OtpVerification
import com.emamagic.domain.interactors.PhoneNumberRegistration
import com.emamagic.domain.interactors.UpdateServerConfig
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.SigninEffect
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.phone.SigninWithPhoneFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val updateServerConfig: UpdateServerConfig,
    private val otpVerification: OtpVerification,
    private val phoneNumberRegistration: PhoneNumberRegistration,
): BaseViewModel<SigninState, SigninAction>() {

    private lateinit var G_phoneNumber: String

    init {
        getServerConfigEvent()
    }

    // ---------------- singin with phone ----------------
    fun typingPhoneNumberEvent(input: String) = withUseCaseScope {
            if ((input.length == 10 && input.first() != '0') || (input.length == 11 && input.first() == '0')) {
                setEffect { BaseEffect.HideKeyboard }
                setEffect { BaseEffect.EnableUiComponent }
            } else if (input.length == 9 || (input.length == 11 && input.first() != '0')){
                setEffect { BaseEffect.DisableUiComponent }
            }
    }

    fun signinWithServerNameClickedEvent() = withUseCaseScope {
        setEffect { BaseEffect.NavigateTo(SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToSigninWithServerNameFragment()) }
    }

    fun signinWithUserNameClickedEvent() = withUseCaseScope {
        setEffect { BaseEffect.NavigateTo(SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToSigninWithUsernameFragment()) }
    }

    fun submitPhoneNumberEvent(phoneNum: String, countryCode: String) = withUseCaseScope {
        if (phoneNum.isNotEmpty() && ((phoneNum.length == 10 && phoneNum.first() == '9') || (phoneNum.length == 11 && phoneNum.first() == '0'))) {

            G_phoneNumber = if (phoneNum.length == 11) countryCode + phoneNum.substring(1)
            else countryCode + phoneNum
            phoneNumberRegistration(PhoneNumberRegistration.Params(PhoneNumber(G_phoneNumber))).manageResult()
        } else {
            setEffect { SigninEffect.InvalidPhoneNumber }
        }
    }

    fun getServerConfigEvent() = withUseCaseScope {
        updateServerConfig(Unit).manageResult()
    }

    fun submitTermsPolicyEvent() = withUseCaseScope {
        setEffect {
            BaseEffect.NavigateTo(
            SigninWithPhoneFragmentDirections
                .actionSigninWithPhoneFragmentToTermsPolicyFragment(R.string.privacy_policy_description)
        )}
    }

    // ---------------- singin with username ----------------

    fun submitUserNameEvent(userName: String, pass: String) = withUseCaseScope {
        if (userName.isEmpty()) {
            setEffect { SigninEffect.InvalidUsername }
            return@withUseCaseScope
        }
        if (pass.isEmpty()) {
            setEffect { SigninEffect.InvalidPass }
            return@withUseCaseScope
        }
    }

    fun typingUserNameOrPassEvent(userName: String, pass: String) = withUseCaseScope {
        if (userName.trim().isNotEmpty() && pass.trim().isNotEmpty()) {
            setEffect { BaseEffect.EnableUiComponent }
        } else {
            setEffect { BaseEffect.DisableUiComponent }
        }
    }

    fun signinWithPhoneClickedEvent() = withUseCaseScope {
        setEffect { BaseEffect.NavigateBack }
    }


    // ---------------- singin with server name ----------------



    // ---------------- otp ----------------

    fun otpExpired() = withUseCaseScope {

    }

    fun submitOtpEvent(code: String) = withUseCaseScope {
        if (code.length != 5) {
            setEffect { SigninEffect.InvalidOtpCode }
            return@withUseCaseScope
        }
        @SuppressLint("HardwareIds")
        val deviceId = Settings.Secure.getString(context.applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        otpVerification(OtpVerification.Params(code, G_phoneNumber, deviceId)).manageResult()
    }

    override fun createInitialState() = SigninState.initialize()

    override fun handleEvent(event: SigninAction) {
        
    }

}