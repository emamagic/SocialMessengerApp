package com.emamagic.safe.policy

import com.emamagic.safe.util.ResultWrapper
import java.util.*

data class MemoryPolicy(
    val shouldRefresh: (oldValue: ResultWrapper<*>) -> Boolean = { false },
    val expires: Long = -1
)  {
    val createAt: Date = Date()
}
