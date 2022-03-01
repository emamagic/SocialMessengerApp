package com.emamagic.application.utils

import android.view.View

object ViewHelper {


    fun hideKeyboard(view: View) {
        val inputManager = ServiceHelper.getInputMethodManager(view.context)
        inputManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}