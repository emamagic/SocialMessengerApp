package com.emamagic.cache.cache

import android.content.SharedPreferences
import com.emamagic.cache.cache.CacheFactory
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow

val pref: SharedPreferences by lazy(LazyThreadSafetyMode.NONE) {
    CacheFactory.get()
}

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}

/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> Hawk.put(key, value)
        //throw UnsupportedOperationException("Not yet implemented")
    }
}

/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else ->  Hawk.get(key, null)
        //throw UnsupportedOperationException("Not yet implemented")
    }
}

private val preferenceKeyChangedFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
    preferenceKeyChangedFlow.tryEmit(key)
}

fun SharedPreferences.toFlow() {
    pref.registerOnSharedPreferenceChangeListener(listener)
}
