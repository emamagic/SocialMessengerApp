package com.emamagic.cache.cache.data_store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.emamagic.cache.cache.CacheInitializer

val store: DataStore<Preferences> by lazy { CacheInitializer.getStore() }

object StoreKeys {
    val SORT_ORDER = stringPreferencesKey("sort_order")
    val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
}






