package com.emamagic.limoo

import androidx.multidex.MultiDexApplication
import com.emamagic.application.utils.view.TypeFaceHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        TypeFaceHelper.generateTypeface(this)
    }

}