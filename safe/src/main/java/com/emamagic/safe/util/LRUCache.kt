package com.emamagic.safe.util

import android.util.Log
import com.emamagic.safe.policy.MemoryPolicy
import com.emamagic.safe.util.ResultWrapper
import java.util.*

private const val CAPACITY = 10
private const val TAG = "LRUCache"

// TODO It is better to implement thread-safe
class LRUCache constructor(
    private val capacity: Int? = CAPACITY
) {
    // for printing the doubleLinkedList
    private val builder = StringBuilder()

    private val map = hashMapOf<String, Node>()
    private var head = Node(null, null, null, null)
    private var tail = Node(null, null, null, null)

    data class Node(
        var prev: Node? = null,
        var next: Node? = null,
        var key: String?,
        var value: ResultWrapper<*>?,
        var memoryPolicy: MemoryPolicy? = MemoryPolicy()
    )

    private fun delete(node: Node) {
        if (node.prev != null) {
            node.prev!!.next = node.next
        } else {
            head = node.next!!
        }
        if (node.next != null) {
            node.next!!.prev = node.prev
        } else {
            tail = node.prev!!
        }
    }

    private fun updateHead(node: Node) {
        node.next = head
        node.prev = null
        head.prev = node
        head = node
        tail = head
    }

    fun put(key: String, value: ResultWrapper<*>, memoryPolicy: MemoryPolicy? = null) {
        if (map.containsKey(key)) {
            val old: Node = map[key]!!
            old.memoryPolicy = memoryPolicy
            old.value = value
            delete(old)
            updateHead(old)
        } else {
            val newNode = Node(key = key, value = value, memoryPolicy = memoryPolicy)
            if (map.size >= capacity!!) {
                map.remove(tail.key)
                // remove last node
                delete(tail)
                updateHead(newNode)
            } else {
                updateHead(newNode)
            }
            map[key] = newNode
        }
    }

    operator fun get(key: String): ResultWrapper<*>? {
        if (map.containsKey(key)) {
            val n: Node = map[key]!!
            delete(n)
            if (!n.memoryPolicy!!.shouldRefresh(n.value!!) && !isExpired(n)) {
                updateHead(n)
                return n.value
            }
            map.remove(key)
        }
        return null
    }

    private fun isExpired(node: Node): Boolean {
        val policy = node.memoryPolicy!!
        val result = if (policy.expires != -1L) {
            (Date().time - policy.createAt.time) > policy.expires
        } else false
        Log.e(TAG, "isExpired: $result")
        return result
    }

    fun print() {
        val currentValue: ResultWrapper<*>? = head.value
        if (currentValue == null) {
            builder.append("[]")
        } else {
            builder.append('[')
            var currentNode: Node? = head
            while (currentNode?.value != null) {
                builder.append(currentNode.value).append(", ")
                currentNode = currentNode.next
            }
            builder.delete(builder.length - 2, builder.length)
            builder.append(']')
        }
        Log.e(TAG, "linked list items -> $builder")
    }

}