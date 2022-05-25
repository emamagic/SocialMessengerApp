package com.emamagic.signup.contract.redux

import com.emamagic.mvi.BaseStore
import com.emamagic.signup.contract.SignupAction
import com.emamagic.signup.contract.SignupState
import javax.inject.Inject

class SignupStore @Inject constructor(
    private val signupMiddleware: SignupMiddleware
): BaseStore<SignupState, SignupAction>(
  initialState = SignupState.initialize() ,
  reducer = SignupReducer(),
  middlewares = listOf(signupMiddleware)
)