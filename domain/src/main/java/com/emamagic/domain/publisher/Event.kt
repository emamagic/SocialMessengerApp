package com.emamagic.domain.publisher

import com.emamagic.domain.publisher.IDS.LOGOUT

class Event(val id: Int, val data: Any? = null, val callback: (() -> Unit?)? = null) {
    companion object {
        val IDS = listOf(LOGOUT)
    }
}