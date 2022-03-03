package com.emamagic.application.base

import com.emamagic.application.utils.Logger
import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.common_jvm.succeeded

abstract class BaseMiddleware<STATE : State, ACTION : Action> {

    abstract suspend fun process(
        action: ACTION,
        currentState: STATE,
        store: Store<STATE, ACTION>,
    )


    suspend fun <T> ResultWrapper<T>.manageResult(store: Store<STATE, ACTION>): T? {
        if (succeeded) return data
        store.setEffect(BaseEffect.Toast(error?.message ?: "unKnown error"))
        Logger.e("Error Happened", error?.message)
        return null
    }

}
