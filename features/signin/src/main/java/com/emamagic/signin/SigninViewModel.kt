package com.emamagic.signin

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.viewModelScope
import com.emamagic.application.base.BaseEffect
import com.emamagic.application.base.BaseViewModel
import com.emamagic.application.base.SigninEffect
import com.emamagic.entity.PhoneNumber
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.contract.redux.SigninStore
import com.emamagic.signin.otp.OtpFragmentDirections
import com.emamagic.signin.phone.SigninWithPhoneFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SigninViewModel @Inject constructor(
    @ApplicationContext
    private val app: Context,
    private val store: SigninStore
): BaseViewModel<SigninState, SigninAction>(store = store) {

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
            val phoneNumber = if (phoneNum.length == 11)
                PhoneNumber(countryCode + phoneNum.substring(1))
            else
                PhoneNumber(countryCode + phoneNum)
            store.dispatch(SigninAction.SubmitPhoneNumber(phoneNumber))
        } else {
            store.setEffect(SigninEffect.InvalidPhoneNumber)
        }
    }

    fun getServerConfigEvent(hostName: String) = viewModelScope.launch {
        store.dispatch(SigninAction.GetServerConfig(hostName))
    }

    fun submitTermsPolicyEvent() = viewModelScope.launch {
        store.setEffect(BaseEffect.NavigateTo(
            SigninWithPhoneFragmentDirections
                .actionSigninWithPhoneFragmentToTermsPolicyFragment(app.getString(R.string.privacy_policy_description))
        ))
    }

    // ---------------- singin with username ----------------

    fun submitUserNameEvent() = viewModelScope.launch {

    }

    fun typingUserNameEvent(input: String) = viewModelScope.launch {

    }

    fun typingPasswordEvent(input: String) = viewModelScope.launch {

    }

    // ---------------- singin with server name ----------------



    // ---------------- otp ----------------

    fun otpExpired() = viewModelScope.launch {

    }

    fun submitOtpEvent() = viewModelScope.launch {
        store.setEffect(BaseEffect.NavigateTo(OtpFragmentDirections.actionOtpFragmentToConversationFragment()))
    }

}