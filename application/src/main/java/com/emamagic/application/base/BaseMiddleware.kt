package com.emamagic.application.base

import androidx.annotation.CallSuper
import com.emamagic.application.utils.Logger
import com.emamagic.application.utils.exhaustive
import com.emamagic.application.utils.toast.ToastyMode
import com.emamagic.common_jvm.ResultWrapper
import com.emamagic.entity.Error

abstract class BaseMiddleware<STATE : State, ACTION : Action> {

    lateinit var store: Store<STATE, ACTION>

    @CallSuper
    open suspend fun process(
        action: ACTION,
        currentState: STATE,
        store: Store<STATE, ACTION>,
    ) { this.store = store }

    protected suspend fun <T> ResultWrapper<T>.manageResult(success: (T) -> Unit) {
        store.setEffect(BaseEffect.ShowLoading())
        when (this) {
            is ResultWrapper.Success -> {
                success(data!!)
                store.setEffect(BaseEffect.HideLoading)
            }
            is ResultWrapper.Failed -> onError(error!!)
            is ResultWrapper.LoadingFetch -> TODO()
            ResultWrapper.Loading -> TODO()
        }.exhaustive
    }

    private suspend fun onError(error: Error) {
        var message = "Unknown Error"
        if (!error.display_message.isNullOrEmpty()) message = error.display_message!!
        else if(!error.message.isNullOrEmpty()) message = error.message!!
        Logger.e("Error -> message: $message  statusCode: ${error.statusCode}")
        store.setEffect(BaseEffect.Toast(message, ToastyMode.MODE_TOAST_ERROR))
    }



}
