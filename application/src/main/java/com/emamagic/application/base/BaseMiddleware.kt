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

    protected suspend fun <T> ResultWrapper<T>.manageResult(supportLoading: Boolean = true, success: suspend (T?) -> Unit) {
        if (supportLoading) store.setEffect(BaseEffect.ShowLoading())
        when (this) {
            is ResultWrapper.Success -> {
                success(data)
            }
            is ResultWrapper.Failed -> {
                onError(error!!)
            }
            is ResultWrapper.LoadingFetch -> TODO()
            ResultWrapper.Loading -> TODO()

        }.exhaustive
        if (supportLoading) store.setEffect(BaseEffect.HideLoading)
    }

    private suspend fun onError(error: Error) {
        val message: String = if (!error.display_message.isNullOrEmpty()) error.display_message!!
        else if(!error.message.isNullOrEmpty()) error.message!!
        else "${error.throwable?.message}  ${error.throwable?.cause} \n ${error.throwable?.stackTraceToString()}"
        Logger.e("Error -> message: $message  statusCode: ${error.statusCode} errorType: ${error.errorType}")
        store.setEffect(BaseEffect.Toast("Error Happened", ToastyMode.MODE_TOAST_ERROR))
    }



}
