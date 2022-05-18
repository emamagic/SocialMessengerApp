package com.emamagic.signin

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import androidx.lifecycle.viewModelScope
import com.emamagic.mvi.BaseEffect
import com.emamagic.base.base.BaseViewModel
import com.emamagic.mvi.SigninEffect
import com.emamagic.entity.PhoneNumber
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.contract.redux.SigninStore
import com.emamagic.signin.phone.SigninWithPhoneFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SigninViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context,
    private val store: SigninStore
): BaseViewModel<SigninState, SigninAction>(store = store) {

    private lateinit var G_phoneNumber: String

    init {
        getServerConfigEvent("https://test.limonadapp.ir")
    }

    // ---------------- singin with phone ----------------
    fun typingPhoneNumberEvent(input: String) = viewModelScope.launch {
            if ((input.length == 10 && input.first() != '0') || (input.length == 11 && input.first() == '0')) {
                store.setEffect(BaseEffect.HideKeyboard)
                store.setEffect(BaseEffect.EnableUiComponent)
            } else if (input.length == 9 || (input.length == 11 && input.first() != '0')){
                store.setEffect(BaseEffect.DisableUiComponent)
            }
    }

    fun signinWithServerNameClickedEvent() = viewModelScope.launch {
        store.setEffect(BaseEffect.NavigateTo(SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToSigninWithServerNameFragment()))
    }

    fun signinWithUserNameClickedEvent() = viewModelScope.launch {
        store.setEffect(BaseEffect.NavigateTo(SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToSigninWithUsernameFragment()))
    }

    fun submitPhoneNumberEvent(phoneNum: String, countryCode: String) = viewModelScope.launch {
        if (phoneNum.isNotEmpty() && ((phoneNum.length == 10 && phoneNum.first() == '9') || (phoneNum.length == 11 && phoneNum.first() == '0'))) {

            G_phoneNumber = if (phoneNum.length == 11) countryCode + phoneNum.substring(1)
            else countryCode + phoneNum

            store.dispatch(SigninAction.SubmitPhoneNumberRegistration(PhoneNumber(G_phoneNumber)))
        } else {
            store.setEffect(SigninEffect.InvalidPhoneNumber)
        }
    }

    fun getServerConfigEvent(hostName: String) = viewModelScope.launch {
        store.dispatch(SigninAction.GetServerConfig(hostName))
    }

    fun submitTermsPolicyEvent() = viewModelScope.launch {
        store.setEffect(
            BaseEffect.NavigateTo(
            SigninWithPhoneFragmentDirections
                .actionSigninWithPhoneFragmentToTermsPolicyFragment(R.string.privacy_policy_description)
        ))
    }

    // ---------------- singin with username ----------------

    fun submitUserNameEvent(userName: String, pass: String) = viewModelScope.launch {
        if (userName.isEmpty()) {
            store.setEffect(SigninEffect.InvalidUsername)
            return@launch
        }
        if (pass.isEmpty()) {
            store.setEffect(SigninEffect.InvalidPass)
            return@launch
        }
    }

    fun typingUserNameOrPassEvent(userName: String, pass: String) = viewModelScope.launch {
        if (userName.trim().isNotEmpty() && pass.trim().isNotEmpty()) {
            store.setEffect(BaseEffect.EnableUiComponent)
        } else {
            store.setEffect(BaseEffect.DisableUiComponent)
        }
    }

    fun signinWithPhoneClickedEvent() = viewModelScope.launch {
        store.setEffect(BaseEffect.NavigateBack)
    }


    // ---------------- singin with server name ----------------



    // ---------------- otp ----------------

    fun otpExpired() = viewModelScope.launch {

    }

    fun submitOtpEvent(code: String) = viewModelScope.launch {
        if (code.length != 5) {
            store.setEffect(SigninEffect.InvalidOtpCode)
            return@launch
        }
        @SuppressLint("HardwareIds")
        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        store.dispatch(SigninAction.SubmitOtpVerification(code, G_phoneNumber, deviceId))
    }

}