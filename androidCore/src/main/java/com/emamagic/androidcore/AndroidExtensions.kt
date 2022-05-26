package com.emamagic.androidcore

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
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

fun Fragment.hasPermission(vararg permissions: String): Boolean {
    for (permission in permissions) {
        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED)
            return false
    }
    return true
}

fun Fragment.hasPermission(permission: String) : Boolean =
    ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED

fun Fragment.getPermissions(
    permissionListener: PermissionListener,
    vararg permissions: String
) {
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            permissionListener.onPermissionGranted(it)
        }
    requestPermissionLauncher.launch(permissions)
}

fun Fragment.getPermission(
    permission: String,
    permissionListener: PermissionListener2
) {
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            permissionListener.onPermissionGranted(it)
        }
    requestPermissionLauncher.launch(permission)
}

inline fun <reified T : Context> Context.findBaseContext(): T? {
    var ctx: Context? = this
    do {
        if (ctx is T) {
            return ctx
        }
        if (ctx is ContextWrapper) {
            ctx = ctx.baseContext
        }
    } while (ctx != null)

    // If we reach here, there's not an Context of type T in our Context hierarchy
    return null
}

fun Activity.hideSoftInput() {
    val imm: InputMethodManager? = getSystemService()
    val currentFocus = currentFocus
    if (currentFocus != null && imm != null) {
        imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }
}

fun Fragment.hideSoftInput() = requireActivity().hideSoftInput()

