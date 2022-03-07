package com.emamagic.application.base

import androidx.annotation.CallSuper

abstract class BaseMiddleware<STATE : State, ACTION : Action> {

    private lateinit var store: Store<STATE, ACTION>

    @CallSuper
    open suspend fun process(
        action: ACTION,
        currentState: STATE,
        store: Store<STATE, ACTION>,
    ) { this.store = store }

    open fun onError(throwable: Throwable) {

    }

}
