package com.emamagic.mvi

import android.util.Log
import androidx.annotation.CallSuper
import com.emamagic.core.Error
import com.emamagic.core.ResultWrapper
import java.net.HttpURLConnection

abstract class BaseMiddleware<STATE : State, ACTION : Action> {

    lateinit var store: Store<STATE, ACTION>

    @CallSuper
    open suspend fun process(
        action: ACTION,
        currentState: STATE,
        store: Store<STATE, ACTION>,
    ) {
        this.store = store
    }

    protected suspend fun <T> ResultWrapper<T>.manageResult(success: (suspend (T) -> Unit)? = null): Boolean {
        (return when (this) {
            is ResultWrapper.Success -> {
                success?.invoke(data!!)
                true
            }
            is ResultWrapper.Failed -> {
                store.setEffect(BaseEffect.HideLoading)
                onError(error!!)
                false
            }
            is ResultWrapper.LoadingFetch -> TODO()
            ResultWrapper.Loading -> TODO()

        })
    }

    private suspend fun onError(error: Error) {
        val message: String = if (!error.display_message.isNullOrEmpty()) error.display_message!!
        else if (!error.message.isNullOrEmpty()) error.message!!
        else "${error.throwable?.message}  ${error.throwable?.cause} \n ${error.throwable?.stackTraceToString()}"
        Log.e("TAG", "Error -> message: $message  statusCode: ${error.statusCode} errorType: ${error.errorType}")
        when (error.statusCode) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                store.setEffect(BaseEffect.NeedToSignIn)
            }
            427 -> {
                store.setEffect(BaseEffect.NeedToSignUp)
            }
            else -> {
                store.setEffect(
                    BaseEffect.Toast(
                        "Error Happened"
                    )
                )
            }
        }
    }



}
