package com.emamagic.data_android.interceptor.db

interface DatabaseTransactionRunner {
    suspend operator fun <T> invoke(block: suspend () -> T): T
}
