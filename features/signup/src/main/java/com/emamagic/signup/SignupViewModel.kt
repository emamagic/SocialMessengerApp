package com.emamagic.signup

import com.emamagic.base.base.BaseViewModel
import com.emamagic.signup.contract.SignupAction
import com.emamagic.signup.contract.SignupState
import com.emamagic.signup.contract.redux.SignupStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val store: SignupStore
): BaseViewModel<SignupState, SignupAction>(store) {




}