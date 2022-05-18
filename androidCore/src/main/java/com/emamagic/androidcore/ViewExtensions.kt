package com.emamagic.androidcore

import android.content.Context
import android.text.*
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.emamagic.androidcore.display.DisplaySize

@Suppress("LiftReturnOrAssignment")
infix fun View.visible(visible: Boolean) =
    if (visible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }

infix fun ViewGroup.inflate(id: Int): View =
    LayoutInflater.from(context).inflate(id, this, false)

fun View.getDisplaySize(): DisplaySize =
    DisplaySize(
        context.resources.displayMetrics.heightPixels,
        context.resources.displayMetrics.widthPixels
    )

infix fun View.getCenterX(container: View): Int {

    val fromLocation = IntArray(2)
    getLocationInWindow(fromLocation)

    val containerLocation = IntArray(2)
    container.getLocationInWindow(containerLocation)

    val relativeLeft = fromLocation[0] - containerLocation[0]
    fromLocation[1] - containerLocation[1]

    return width / 2 + relativeLeft
}

infix fun View.getCenterY(container: View): Int {

    val fromLocation = IntArray(2)
    getLocationInWindow(fromLocation)

    val containerLocation = IntArray(2)
    container.getLocationInWindow(containerLocation)

    val relativeTop = fromLocation[1] - containerLocation[1]

    return height / 2 + relativeTop
}

var View.transitionNameCompat: String?
    get() = ViewCompat.getTransitionName(this)
    set(value) = ViewCompat.setTransitionName(this, value)

fun Context.getDrawableCompat(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(this, resId)


fun Fragment.getDrawableCompat(@DrawableRes resId: Int) =
    ContextCompat.getDrawable(requireContext(), resId)

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
    this.addDrawerListener(object : DrawerLayout.DrawerListener {
        override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            listener(slideOffset, drawerView)
        }

        override fun onDrawerOpened(drawerView: View) {}
        override fun onDrawerClosed(drawerView: View) {}
        override fun onDrawerStateChanged(newState: Int) {}
    })
}


private fun FragmentManager.isOnBackStack(backStackName: String): Boolean {
    val backStackCount = backStackEntryCount
    for (index in 0 until backStackCount) {
        if (getBackStackEntryAt(index).name == backStackName) {
            return true
        }
    }
    return false
}

inline fun TextView.clickPartOfText(
    text: String,
    color: Int? = null,
    isUnderline: Boolean = false,
    crossinline call: () -> Unit
) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(p0: View) {
            call()
        }

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


//fun Fragment.toasty(title: String, @ToastyMode selectedMode: Int? = null) {
//    if (selectedMode == null || selectedMode == 0) { // ToastyMode.MODE_TOAST_DEFAULT
//        Toast.makeText(requireContext(), title, Toast.LENGTH_LONG).show()
//        return
//    }
//    val layout = layoutInflater.inflate(
//        R.layout.layout_toast,
//        requireView().findViewById(R.id.toast_root)
//    )
//    val toastyType = properToastyType(selectedMode)
//    layout.findViewById<ImageView>(R.id.toast_img)
//        .setImageResource(toastyType.icon)
//    layout.findViewById<ConstraintLayout>(R.id.toast_root)
//        .setBackgroundResource(toastyType.background)
//
//    layout.findViewById<TextView>(R.id.toast_txt).text = title
//    Toast(requireActivity()).apply {
//        setGravity(Gravity.BOTTOM, 0, 100)
//        duration = Toast.LENGTH_LONG
//        view = layout
//    }.show()
//}
//
//fun Fragment.showAlert(
//    message: String,
//    accept: String?,
//    decline: String?,
//    canBeDismiss: Boolean = false,
//    callback: AreYouSureCallback? = null
//): AlertDialog {
//    val dialogBuilder = AlertDialog.Builder(requireContext()).create()
//    dialogBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//    dialogBuilder.setCancelable(false)
//    val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_alert, null)
//    //   dialogBuilder.window!!.attributes.windowAnimations = R.style.DialogAnimation
//    val msg = dialogView?.findViewById<TextView>(R.id.alert_message)
//    val success = dialogView?.findViewById<Button>(R.id.alert_done)
//    val cancel = dialogView?.findViewById<Button>(R.id.alert_cancel)
//    val alertTitle = dialogView?.findViewById<TextView>(R.id.alert_title)
//    val dismiss = dialogView?.findViewById<ImageButton>(R.id.btnDismiss)
//    msg?.text = message
//    if (accept == null && decline == null) {
//        cancel?.visibility = View.GONE
//        success?.text = "تایید"
//    } else {
//        if (accept == null) {
//            success?.visibility = View.GONE
//        } else {
//            success?.text = accept
//        }
//        if (decline == null) {
//            cancel?.visibility = View.GONE
//        } else {
//            cancel?.text = decline
//        }
//    }
//    if (canBeDismiss) {
//        dismiss?.visibility = View.VISIBLE
//    } else {
//        dismiss?.visibility = View.GONE
//    }
//    if (callback != null) {
//        cancel?.setOnClickListener { callback.cancel() }
//        success?.setOnClickListener { callback.proceed() }
//        dismiss?.setOnClickListener { dialogBuilder?.dismiss() }
//    } else {
//        cancel?.setOnClickListener { dialogBuilder?.dismiss() }
//        success?.setOnClickListener { dialogBuilder?.dismiss() }
//        dismiss?.setOnClickListener { dialogBuilder?.dismiss() }
//    }
//
//
//    dialogBuilder?.setView(dialogView)
//    dialogBuilder?.show()
//    return dialogBuilder
//}


