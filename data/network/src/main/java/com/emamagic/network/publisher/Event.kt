package com.emamagic.network.publisher

import com.emamagic.network.util.Const.LOGGED_OUT
import com.emamagic.network.util.Const.TEST

class Event(val id: Int, val data: Any? = null, val callback: (() -> Unit?)? = null) {
    companion object {
        val IDS = listOf(LOGGED_OUT, TEST)
    }
}