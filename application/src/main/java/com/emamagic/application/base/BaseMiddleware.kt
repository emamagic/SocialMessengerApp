package com.emamagic.application.base

abstract class BaseMiddleware<STATE : State, ACTION : Action> {

    abstract suspend fun process(
        action: ACTION,
        currentState: STATE,
        store: Store<STATE, ACTION>,
    )
    // todo Error handling

}
