package com.emamagic.base.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.emamagic.core.*
import com.emamagic.mvi.EVENT
import com.emamagic.mvi.BaseEffect
import com.emamagic.mvi.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.HttpURLConnection

abstract class BaseViewModel<STATE : State, ACTION : EVENT>: ViewModel(),
    CoroutinesLoadingRunner {

    override val useCaseScope: CoroutineScope
        get() = viewModelScope

    // Create Initial State of View
    private val initialState: STATE by lazy { createInitialState() }
    abstract fun createInitialState(): STATE

    // Get Current State
    val currentState: STATE
        get() = uiState.value

    private val _uiState: MutableStateFlow<STATE> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _uiEvent: MutableSharedFlow<ACTION> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _uiEffect: Channel<BaseEffect> = Channel()
    val uiEffect = _uiEffect.receiveAsFlow()

    fun setEvent(event: ACTION) {
        val newEvent = event
        viewModelScope.launch { _uiEvent.emit(newEvent) }
    }

    protected fun setState(reduce: STATE.() -> STATE) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> BaseEffect) {
        val effectValue = builder()
        viewModelScope.launch { _uiEffect.send(effectValue) }
    }


    init {
        subscribeEvents()
    }

    private fun subscribeEvents() = viewModelScope.launch {
        uiEvent.collect {
            handleEvent(it)
        }
    }

    abstract fun handleEvent(event: ACTION)

    open fun navigateTo(directions: NavDirections) {
        setEffect { BaseEffect.NavigateTo(directions) }
    }

    protected suspend fun <T> ResultWrapper<T>.manageResult(success: (suspend (T) -> Unit)? = null) {
        when (this) {
            is ResultWrapper.Success -> {
                success?.invoke(data!!)
            }
            is ResultWrapper.Failed -> {
                setEffect { BaseEffect.HideLoading() }
                onError(error!!)
            }
            is ResultWrapper.LoadingFetch -> TODO()

        }
    }

    private fun onError(error: Error) {
        val message: String = if (!error.display_message.isNullOrEmpty()) error.display_message!!
        else if (!error.message.isNullOrEmpty()) error.message!!
        else "${error.throwable?.message}  ${error.throwable?.cause} \n ${error.throwable?.stackTraceToString()}"
        Log.e("TAG", "Error -> message: $message  statusCode: ${error.statusCode} errorType: ${error.errorType}")
        when (error.statusCode) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                setEffect { BaseEffect.NeedToSignIn }
            }
            427 -> {
                setEffect { BaseEffect.NeedToSignUp }
            }
            else -> {
                setEffect {
                    BaseEffect.Toast(
                        "Error Happened"
                    )
                }
            }
        }
    }

    override fun withLoadingScope(
        loadingUpdater: ((Boolean) -> Unit)?,
        onError: ((Throwable) -> Unit)?,
        onComplete: (() -> Unit)?,
        block: suspend () -> Unit
    ) {
        super.withLoadingScope(
            loadingUpdater = {
                setEffect { BaseEffect.ShowLoading() }
            },
            onError = {

            },
            onComplete = {
                setEffect { BaseEffect.HideLoading() }
            },
            block = block)
    }


}