package com.emamagic.signin.contract.redux

import com.emamagic.application.base.BaseStore
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import com.emamagic.signin.contract.redux.middleware.OtpMiddleware
import com.emamagic.signin.contract.redux.middleware.SigninWithPhoneBaseMiddleware
import javax.inject.Inject

class SigninStore @Inject constructor(
    signinWithPhoneMiddleware: SigninWithPhoneBaseMiddleware,
    otpMiddleware: OtpMiddleware
) : BaseStore<SigninState, SigninAction>(
    initialState = SigninState.initialize(),
    reducer = SigninReducer(),
    middlewares = listOf(
        signinWithPhoneMiddleware,
        otpMiddleware
    )
)