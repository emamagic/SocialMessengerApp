package com.emamagic.cache.cache

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.preference.PreferenceManager
import com.emamagic.cache.User
import com.emamagic.cache.cache.data_store.proto.UserSerializer
import com.orhanobut.hawk.Hawk

object CacheInitializer {

    @Volatile
    private lateinit var app: Application

    private val Context.dataStore by preferencesDataStore(name = "com.emamagic.Limoo_store")

    fun init(application: Application) {
        app = application
        Hawk.init(app)
    }

    fun getPrefs(): SharedPreferences {
        if (!this::app.isInitialized) throw Exception("PrefInitializer Does Not Initialize")
        return PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
    }

    fun getStore(): DataStore<Preferences> {
        if (!this::app.isInitialized) throw Exception("PrefInitializer Does Not Initialize")
        return app.dataStore
    }


 //////////////////////////////////// PROTO ////////////////////////////////////

    private val Context.userStore: DataStore<User> by dataStore(
        fileName = "user.pb", // .json
        serializer = UserSerializer
    )

    fun getUserStore(): DataStore<User> {
        if (!this::app.isInitialized) throw Exception("PrefInitializer Does Not Initialize")
        return app.userStore
    }

}