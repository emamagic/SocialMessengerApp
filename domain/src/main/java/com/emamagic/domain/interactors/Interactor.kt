package com.emamagic.domain.interactors

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emamagic.core.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

abstract class Interactor<in ParamsType>(private val dispatcher: AppCoroutineDispatchers) {

    suspend operator fun invoke(
        params: ParamsType
    ) = withContext(dispatcher.io) {
        buildUseCase(params)
    }

    protected abstract suspend fun buildUseCase(params: ParamsType): ResultWrapper<*>

}

abstract class ResultInteractor<in ParamsType, ReturnType>(private val dispatcher: AppCoroutineDispatchers) {
    suspend operator fun invoke(params: ParamsType): ResultWrapper<ReturnType> =
        withContext(dispatcher.io) {
            buildUseCase(params)
        }

    protected abstract suspend fun buildUseCase(params: ParamsType): ResultWrapper<ReturnType>
}

abstract class PagingInteractor<ParamsType : PagingInteractor.Parameters<T>, T : Any> :
    SubjectInteractor<ParamsType, PagingData<T>>() {
    interface Parameters<T : Any> {
        val pagingConfig: PagingConfig
    }
}

abstract class SuspendingWorkInteractor<ParamsType : Any, T> : SubjectInteractor<ParamsType, T>() {
    override fun createObservable(params: ParamsType): Flow<T> = flow {
        emit(buildUseCase(params))
    }

    abstract suspend fun buildUseCase(params: ParamsType): T
}

abstract class SubjectInteractor<ParamsType : Any, T> {
    // Ideally this would be buffer = 0, since we use flatMapLatest below, BUT invoke is not
    // suspending. This means that we can't suspend while flatMapLatest cancels any
    // existing flows. The buffer of 1 means that we can use tryEmit() and buffer the value
    // instead, resulting in mostly the same result.
    private val paramState = MutableSharedFlow<ParamsType>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val flow: Flow<T> = paramState
        .distinctUntilChanged()
        .flatMapLatest { createObservable(it) }
        .distinctUntilChanged()

    operator fun invoke(params: ParamsType) {
        paramState.tryEmit(params)
    }

    protected abstract fun createObservable(params: ParamsType): Flow<T>
}