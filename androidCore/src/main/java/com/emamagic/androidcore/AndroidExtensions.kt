package com.emamagic.androidcore

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Context.getScreenWidth(): Int =
    resources.displayMetrics.widthPixels

fun Context.getScreenHeight(): Int =
    resources.displayMetrics.widthPixels

infix fun Float.pixel(context: Context): Float =
    this * (context.resources.displayMetrics.densityDpi.toFloat() /
            DisplayMetrics.DENSITY_DEFAULT)

infix fun Float.dp(context: Context): Float =
    this / (context.resources.displayMetrics.densityDpi.toFloat() /
            DisplayMetrics.DENSITY_DEFAULT)

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    requireView().hideKeyboard()
}

fun Fragment.showKeyboard() {
    requireView().showKeyboard()
}

fun View.showKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}