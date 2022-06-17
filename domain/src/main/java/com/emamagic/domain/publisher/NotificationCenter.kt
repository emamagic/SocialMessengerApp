package com.emamagic.domain.publisher

object NotificationCenter {
    interface NotificationCenterDelegate {
        fun receiveData(event: Event)
    }
    private val subscribers = Event.IDS.map { mutableListOf<NotificationCenterDelegate>() }

    fun subscribe(subscriber: NotificationCenterDelegate, id: Int) = subscribers[id].add(subscriber)

    fun unSubscribe(subscriber: NotificationCenterDelegate, id: Int) = subscribers[id].remove(subscriber)

    fun notifySubscribers(event: Event) = subscribers[event.id].forEach { it.receiveData(event) }
}