package com.emamagic.core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

val <T> T.exhaustive: T
    get() = this
