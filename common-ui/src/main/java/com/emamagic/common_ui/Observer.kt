package com.emamagic.common_ui

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext


fun LifecycleOwner.addRepeatingJob(
    state: Lifecycle.State,
    block: suspend CoroutineScope.() -> Unit
): Job {
    return lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(state, block)
    }
}

inline fun <T> Flow<T>.collectWhileStarted(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {
        collect { action(it) }
    }
}

inline fun <T> Flow<T>.collectWhileResumed(
    lifecycleOwner: LifecycleOwner,
    crossinline action: suspend (value: T) -> Unit
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.RESUMED) {
        collect { action(it) }
    }
}

fun <T> Flow<T>.collectWhileStartedIn(
    lifecycleOwner: LifecycleOwner
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {}
}

fun <T> Flow<T>.collectWhileResumedIn(
    lifecycleOwner: LifecycleOwner
) {
    lifecycleOwner.addRepeatingJob(Lifecycle.State.STARTED) {}
}

