package com.emamagic.common_ui.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface CoroutinesLoadingRunner {

    val useCaseScope: CoroutineScope

    fun withLoadingScope(
        loadingUpdater: ((Boolean) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        block: (suspend () -> Unit)
    ) {
        useCaseScope.launch {
            loadingUpdater?.invoke(true)
            try {
                block()
            } catch (t: Throwable) {
                onError?.invoke(t)
            } finally {
                loadingUpdater?.invoke(false)
                onComplete?.invoke()
            }
        }
    }
}