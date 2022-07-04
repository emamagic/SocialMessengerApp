package com.emamagic.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch


fun <T> Flow<T>.handleErrors(): Flow<T> =
    catch { e -> Log.e("TAG", "handleErrors: ${e.stackTraceToString()}") }