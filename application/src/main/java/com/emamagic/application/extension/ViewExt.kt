package com.emamagic.application.extension

import android.content.Context
import android.text.*
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.lang.ref.WeakReference

fun View.gone() {
    if (isVisible)
        visibility = View.GONE
}

fun View.inVisible() {
    if (isVisible)
        visibility = View.INVISIBLE
}

fun View.visible() {
    if (!isVisible)
        visibility = View.VISIBLE
}

fun Context.setColor(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

fun Fragment.setColor(@ColorRes color: Int) =
    ContextCompat.getColor(requireContext(), color)

inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                f()
            }
        }
    })
}

fun isFragmentVisible(fragment: WeakReference<Fragment>): Boolean =
    (fragment.get() != null && fragment.get()!!.activity != null &&
            fragment.get()!!.isVisible && !fragment.get()!!.isRemoving)


//fun Context.appIsInBackground(): Boolean {
//    return if (applicationContext is OnAppVisibility) {
//        (applicationContext as OnAppVisibility).appIsInBackground()
//    } else {
//        throw IllegalStateException("Provide the application context which implement App Visibility")
//    }
//}
//
//fun View.appIsInBackground(): Boolean = context.appIsInBackground()
//
//fun Fragment.appIsInBackground(): Boolean = requireContext().appIsInBackground()

inline fun EditText.onTextChange(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            listener(s.toString())
        }
    })
}

inline fun DrawerLayout.onDrawerListener(crossinline listener: (Float, View) -> Unit) {
    this.addDrawerListener(object: DrawerLayout.DrawerListener{
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) { listener(slideOffset ,drawerView) }
        override fun onDrawerOpened(drawerView: View) {}
        override fun onDrawerClosed(drawerView: View) {}
        override fun onDrawerStateChanged(newState: Int) {}
    })
}

val <T> T.exhaustive: T
    get() = this


private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

inline fun TextView.clickPartOfText(text: String, color: Int? = null, isUnderline: Boolean = false, crossinline call: ()-> Unit) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) { call() }
        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            color?.let { ds.color = it }
            ds.isUnderlineText = isUnderline
            ds.isFakeBoldText = true
        }
    }
    val startIndex = getText().indexOf(text)
    val endIndex = startIndex + text.length
    val spannableString = SpannableString(getText())
    spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    setText(spannableString)
}
