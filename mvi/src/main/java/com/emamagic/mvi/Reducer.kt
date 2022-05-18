package com.emamagic.mvi

interface Reducer<STATE : State, ACTION : Action> {

    fun reduce(currentState: STATE, action: ACTION): STATE
}
