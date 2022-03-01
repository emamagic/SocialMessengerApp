package com.emamagic.application.base

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface Store<STATE : State, ACTION : Action> {

    val state: StateFlow<STATE>

    val effect: Channel<BaseEffect>

    suspend fun dispatch(action: ACTION)

    suspend fun setEffect(effect: BaseEffect)

}
