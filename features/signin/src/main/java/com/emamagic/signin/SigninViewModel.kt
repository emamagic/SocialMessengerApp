package com.emamagic.signin

import androidx.lifecycle.viewModelScope
import com.emamagic.application.base.BaseEffect
import com.emamagic.application.base.BaseViewModel
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.contract.redux.SigninStore
import com.emamagic.signin.otp.OtpFragmentDirections
import com.emamagic.signin.phone.SigninWithPhoneFragmentDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val store: SigninStore
): BaseViewModel<SigninState, SigninAction>(store = store) {

    init {
//        getServerConfigEvent("https://test.limonadapp.ir")
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
//        store.dispatch()
    }

    fun signinWithUserNameClickedEvent() = viewModelScope.launch {
//        store.dispatch()
    }

    fun submitPhoneNumberEvent() = viewModelScope.launch {
//        store.dispatch(SigninAction.GetServerConfig(""))
        store.setEffect(BaseEffect.NavigateTo(SigninWithPhoneFragmentDirections.actionSigninWithPhoneFragmentToOtpFragment()))
    }

    fun getServerConfigEvent(hostName: String) = viewModelScope.launch {
        store.dispatch(SigninAction.GetServerConfig(hostName))
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