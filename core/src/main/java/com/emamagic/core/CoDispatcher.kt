package com.emamagic.core

import javax.inject.Qualifier

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class CoDispatcher(val type: DispatcherType)

enum class DispatcherType {
    SHARED
}