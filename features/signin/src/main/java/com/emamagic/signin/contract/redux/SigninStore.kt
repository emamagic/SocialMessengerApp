package com.emamagic.signin.contract.redux

import com.emamagic.application.base.BaseStore
import com.emamagic.signin.contract.SigninAction
import com.emamagic.signin.contract.SigninState
import javax.inject.Inject

class SigninStore @Inject constructor(
    signinMiddleware: SigninMiddleware
) : BaseStore<SigninState, SigninAction>(
    initialState = SigninState.initialize(),
    reducer = SigninReducer(),
    middlewares = listOf(
        signinMiddleware
    )
)