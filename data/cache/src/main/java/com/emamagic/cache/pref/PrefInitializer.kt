package com.emamagic.cache.pref

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object PrefInitializer {

    @Volatile
    private lateinit var app: Application

    fun init(application: Application) { app = application }

    fun get(): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(app.applicationContext)

}