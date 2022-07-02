package com.emamagic.cache.cache

import android.app.Application
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.flow.MutableSharedFlow

object CacheFactory {


    @Volatile
    private lateinit var app: Application

    fun create(application: Application) {
        app = application
        Hawk.init(app).build()
    }

    fun get(): SharedPreferences {
        if (!this::app.isInitialized) throw Exception("PrefInitializer Does Not Initialize")
        return PreferenceManager.getDefaultSharedPreferences(app.applicationContext)
    }


}